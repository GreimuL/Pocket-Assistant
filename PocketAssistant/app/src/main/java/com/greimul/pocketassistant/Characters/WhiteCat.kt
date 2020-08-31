package com.greimul.pocketassistant.Characters

import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.WindowManager
import com.greimul.pocketassistant.R
import kotlinx.android.synthetic.main.overlay_character.view.*

class WhiteCat(v: View,chatView:View):CharacterStandard(v,chatView) {
    init {
        v.imageview_overlay_character.apply{
            setBackgroundResource(R.drawable.overlay_char_whitecat)
        }
    }

    override fun changeToMoveAnim():AnimationDrawable {
        v.imageview_overlay_character.apply{
            setBackgroundResource(R.drawable.anim_overlay_whitecat_move)
            return background as AnimationDrawable
        }
    }

    override fun changeToNormalImg() {
        v.imageview_overlay_character.setBackgroundResource(R.drawable.overlay_char_whitecat)
    }
}