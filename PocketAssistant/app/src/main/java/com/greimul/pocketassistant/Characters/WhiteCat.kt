package com.greimul.pocketassistant.Characters

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.bumptech.glide.Glide
import com.greimul.pocketassistant.R
import kotlinx.android.synthetic.main.overlay_character.view.*
import kotlinx.android.synthetic.main.overlay_text_chat.view.*
import kotlin.random.Random

class WhiteCat(v: View,chatView:View,c:Context):CharacterStandard(v,chatView,c) {
    val stdDrawable = c.getDrawable(R.drawable.overlay_char_whitecat)
    val movDrawable = c.getDrawable(R.drawable.anim_overlay_whitecat_move)
    val chatArr = c.resources.getStringArray(R.array.whiteCatChat)
    init {
        v.imageview_overlay_character.apply{
            setImageDrawable(stdDrawable)
        }
    }

    override fun changeToMoveAnim():AnimationDrawable {
        v.imageview_overlay_character.apply{
            setImageDrawable(movDrawable)
            return drawable as AnimationDrawable
        }
    }
    override fun changeToNormalImg() {
        v.imageview_overlay_character.apply{
            setImageDrawable(stdDrawable)
        }
    }

    override fun setRandomChat() {
        chatView.button_chatbox.text = chatArr[Random.nextInt(chatArr.size)]
    }
}