package com.greimul.pocketassistant.Characters

import android.graphics.PixelFormat
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.overlay_character.view.*

open class CharacterStandard(private val v:View,private val chatView:View){
    var paramInit:WindowManager.LayoutParams
    val layoutParams: MutableLiveData<WindowManager.LayoutParams> by lazy {
        MutableLiveData<WindowManager.LayoutParams>()
    }
    init{
        paramInit = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            PixelFormat.TRANSLUCENT).apply{
            gravity = Gravity.CENTER or Gravity.RIGHT
        }
        layoutParams.value = paramInit
    }

    fun getView():View = v

    fun setParams(params:WindowManager.LayoutParams){
        layoutParams.postValue(params)
    }

    open fun changeImage(res:Int){
        v.imageview_overlay_character.apply{
            setBackgroundResource(res)
        }
    }
}