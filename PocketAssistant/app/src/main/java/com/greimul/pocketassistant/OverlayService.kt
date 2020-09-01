package com.greimul.pocketassistant

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Binder
import android.os.IBinder
import android.view.*
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import com.greimul.pocketassistant.Characters.*
import kotlinx.android.synthetic.main.overlay_character.view.*

class OverlayService: LifecycleService() {
    private val binder = LocalBind()

    private lateinit var windowManager: WindowManager
    private lateinit var layoutParams:WindowManager.LayoutParams
    private lateinit var overlayCharView:View
    private lateinit var overlayChatView:View
    lateinit var overlayChar:CharacterStandard
    lateinit var mDetector:GestureDetectorCompat
    lateinit var animMove:AnimationDrawable
    var isAnimStart = false
    var charType:Int? = 0
    inner class LocalBind(): Binder(){
        fun getService():OverlayService = this@OverlayService
    }
    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(overlayCharView)
        windowManager.removeView(overlayChatView)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        overlayCharView = LayoutInflater.from(this).inflate(R.layout.overlay_character,null)
        overlayChatView = LayoutInflater.from(this).inflate(R.layout.overlay_text_chat,null)

        when(CharacterState.charType){
            CharType.WhiteCat->{
                overlayChar = WhiteCat(overlayCharView,overlayChatView)

            }
            CharType.ProtoBoy->{
                overlayChar = ProtoBoy(overlayCharView,overlayChatView)
            }
        }

        layoutParams = overlayChar.paramInitChar
        windowManager.addView(overlayChar.chatView,overlayChar.paramInitChat)
        windowManager.addView(overlayChar.v,overlayChar.paramInitChar)
        overlayChar.layoutParamsChar.observe(this, Observer {
            windowManager.updateViewLayout(overlayChar.v,it)
            layoutParams = it
        })
        overlayChar.layoutParamsChat.observe(this,Observer{
            windowManager.updateViewLayout(overlayChar.chatView,it)
        })

        mDetector = GestureDetectorCompat(this,GestureListener())
        overlayCharView.imageview_overlay_character.setOnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_UP->{
                    overlayChar.changeToNormalImg()
                    isAnimStart = false
                }
            }
            mDetector.onTouchEvent(event)
            true
        }

        return super.onStartCommand(intent, flags, startId)
    }

    inner class GestureListener: GestureDetector.SimpleOnGestureListener() {
        private var preY:Int? =null
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if(!isAnimStart) {
                overlayChar.changeToMoveAnim().start()
                isAnimStart = true
            }


            if(e2!=null) {
                if(preY==null) {
                    preY = e2.rawY.toInt()
                }
                else {
                    preY?.also {
                        layoutParams.y += e2.rawY.toInt() - it
                        overlayChar.setParams(layoutParams)
                    }
                    preY = e2.rawY.toInt()
                }
            }
            return super.onScroll(e1, e2, distanceX, distanceY)
        }
    }
}