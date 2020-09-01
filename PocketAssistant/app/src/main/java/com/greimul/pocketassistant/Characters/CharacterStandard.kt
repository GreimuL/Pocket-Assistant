package com.greimul.pocketassistant.Characters

import android.graphics.PixelFormat
import android.graphics.drawable.AnimationDrawable
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowAnimationFrameStats
import android.view.WindowManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.overlay_character.view.*

open class CharacterStandard(val v:View,val chatView:View){
    var paramInitChar:WindowManager.LayoutParams
    var paramInitChat:WindowManager.LayoutParams
    val layoutParamsChar: MutableLiveData<WindowManager.LayoutParams>
    val layoutParamsChat:MutableLiveData<WindowManager.LayoutParams>
    init{
        paramInitChar = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            PixelFormat.TRANSLUCENT).apply{
            gravity = Gravity.CENTER or Gravity.RIGHT
        }
        paramInitChat = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            PixelFormat.TRANSLUCENT).apply{
            gravity = Gravity.CENTER or Gravity.RIGHT
        }
        paramInitChat.y -= v.height

        layoutParamsChar = MutableLiveData<WindowManager.LayoutParams>(paramInitChar)
        layoutParamsChat = MutableLiveData<WindowManager.LayoutParams>(paramInitChat)
    }

    fun setParams(params:WindowManager.LayoutParams){
        layoutParamsChar.postValue(params)
        val tmpParams = WindowManager.LayoutParams()
        tmpParams.copyFrom(params)
        layoutParamsChat.postValue(tmpParams.apply{
            y -= v.height
        })
    }

    open fun changeImage(res:Int){
        v.imageview_overlay_character.apply{
            setBackgroundResource(res)
        }
    }

    open fun changeToMoveAnim():AnimationDrawable = v.imageview_overlay_character.background as AnimationDrawable
    open fun changeToNormalImg(){}

}