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
import com.google.android.material.theme.MaterialComponentsViewInflater
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
        overlayCharView = View.inflate(this,R.layout.overlay_character,null)
        overlayChatView = View.inflate(ContextThemeWrapper(this,R.style.ChatBox),R.layout.overlay_text_chat,null)

        when(CharacterState.charType){
            CharType.WhiteCat->{
                overlayChar = WhiteCat(overlayCharView,overlayChatView,this)
            }
            CharType.ProtoBoy->{
                overlayChar = ProtoBoy(overlayCharView,overlayChatView,this)
            }
        }

        layoutParams = overlayChar.paramInitChar

        /////////////////////////////////////////////TestCode


        //////////////////////////////////////////////

        windowManager.addView(overlayChar.chatView,overlayChar.paramInitChat)
        windowManager.addView(overlayChar.v,overlayChar.paramInitChar)

        overlayChar.layoutParamsChar.observe(this, Observer {
            windowManager.updateViewLayout(overlayChar.v,it)
            layoutParams = it
        })
        mDetector = GestureDetectorCompat(this,GestureListener())
        overlayCharView.imageview_overlay_character.setOnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_UP->{
                    if(isAnimStart) {
                        overlayChar.changeToNormalImg()
                        val tmpParams = WindowManager.LayoutParams()
                        tmpParams.copyFrom(layoutParams)
                        tmpParams.y -= overlayChar.v.height / 2
                        windowManager.addView(overlayChar.chatView, tmpParams)
                        isAnimStart = false
                    }
                }
            }
            mDetector.onTouchEvent(event)
            true
        }

        return super.onStartCommand(intent, flags, startId)
    }

    inner class GestureListener: GestureDetector.SimpleOnGestureListener() {
        private var preY:Int? =null

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            overlayChar.setRandomChat()
            return super.onSingleTapConfirmed(e)
        }
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if(!isAnimStart){
                windowManager.removeView(overlayChatView)
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

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            return super.onDoubleTap(e)
        }
    }
}