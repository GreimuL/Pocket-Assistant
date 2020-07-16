package com.greimul.crawlertest

import android.app.IntentService
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.graphics.drawable.AnimationDrawable
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.core.view.MotionEventCompat
import kotlinx.android.synthetic.main.view_test.view.*
import kotlinx.android.synthetic.main.view_test2.view.*
import kotlinx.coroutines.*
import kotlin.random.Random

class OverlayService: Service() {

    lateinit var animTest:AnimationDrawable
    lateinit var view:View
    lateinit var view2:View
    private val binder = LocalBind()
    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    inner class LocalBind(): Binder(){
        fun getService():OverlayService = this@OverlayService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        view = LayoutInflater.from(this).inflate(R.layout.view_test,null)
        view2 = LayoutInflater.from(this).inflate(R.layout.view_test2,null)
        view.imageview_main.apply{
            setBackgroundResource(R.drawable.testhide)
            //animTest = background as AnimationDrawable
        }
        //animTest.start()

        val strArr = arrayOf("안녕하세요","테스트에요","초밥이먹고싶어요","초밥주세요","배고파요")
        var paramsTest = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            PixelFormat.TRANSLUCENT).apply{
            gravity = Gravity.CENTER or Gravity.RIGHT
        }
        var paramsTest2 = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            PixelFormat.TRANSLUCENT).apply{
            gravity = Gravity.CENTER or Gravity.RIGHT
        }
        paramsTest2.y = paramsTest.y -200
        var oriX:Float = 0F
        var oriY:Float = 0F
        var imgPreX:Int = 0
        var imgPreY:Int = 0
        var txtPreY = 0
        view.imageview_main.setOnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN->{
                    oriY = event.rawY
                    imgPreY = paramsTest.y
                    txtPreY = paramsTest2.y
                    view2.textview_overlay.text = strArr[Random.nextInt(5)]
                }
                MotionEvent.ACTION_MOVE->{
                    var curY = (event.rawY -oriY).toInt()
                    Log.d("aasff","${event.rawY}")
                    paramsTest.y = imgPreY + curY
                    paramsTest2.y = txtPreY+ curY

                    view2.textview_overlay.text = ""
                    view.imageview_main.apply{
                        setBackgroundResource(R.drawable.anim_test)
                        animTest = background as AnimationDrawable
                    }
                    animTest.start()

                    windowManager.updateViewLayout(view,paramsTest)
                    windowManager.updateViewLayout(view2,paramsTest2)
                }
                MotionEvent.ACTION_UP->{
                    view.imageview_main.apply{
                        setBackgroundResource(R.drawable.testhide)
                    }
                }
                else->{}
            }
            true
        }

        windowManager.addView(view, paramsTest)
        windowManager.addView(view2,paramsTest2)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()

    }

    fun setTimer(hour:Int,minute:Int,second:Int){
        var remainTime = hour*60*60+minute*60+second
        Log.d("titmetei","$remainTime")
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                while(remainTime>0){
                    setText("${remainTime/(60*60)} ${remainTime%(60*60)/60} ${remainTime%(60*60)%60}")
                    remainTime-=1
                    delay(1000L)
                }
            }
        }
    }

    fun setText(showText:String){
        view2.textview_overlay.text = showText
    }
}