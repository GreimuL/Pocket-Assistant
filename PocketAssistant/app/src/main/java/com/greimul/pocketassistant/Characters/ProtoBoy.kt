package com.greimul.pocketassistant.Characters

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.View
import com.greimul.pocketassistant.R
import kotlinx.android.synthetic.main.overlay_character.view.*
import kotlinx.android.synthetic.main.overlay_text_chat.view.*
import kotlin.random.Random

class ProtoBoy(v: View, chatView: View,c:Context):CharacterStandard(v,chatView,c) {
    val stdDrawable = c.getDrawable(R.drawable.overlay_char_protoboy)
    val movDrawable = c.getDrawable(R.drawable.anim_overlay_protoboy_move)
    val chatArr = c.resources.getStringArray(R.array.protoBoyChat)
    init {
        v.imageview_overlay_character.apply{
            setImageDrawable(stdDrawable)
        }
    }

    override fun changeToMoveAnim(): AnimationDrawable {
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