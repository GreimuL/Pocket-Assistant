package com.greimul.pocketassistant.Characters

import android.view.View
import com.greimul.pocketassistant.R
import kotlinx.android.synthetic.main.overlay_character.view.*

class ProtoBoy(v: View, chatView: View):CharacterStandard(v,chatView) {
    init{
        v.imageview_overlay_character.apply{
            setBackgroundResource(R.drawable.overlay_char_protoboy)
        }
    }
}