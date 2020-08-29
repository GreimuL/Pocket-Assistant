package com.greimul.pocketassistant.Characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object CharacterState {

    val charTypeList = listOf(CharType.WhiteCat,CharType.ProtoBoy)

    val charNum = 2

    var charType:CharType = CharType.WhiteCat
    var charTransparency:Int = 0
}