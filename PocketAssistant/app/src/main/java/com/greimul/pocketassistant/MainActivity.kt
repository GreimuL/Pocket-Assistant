package com.greimul.pocketassistant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.greimul.pocketassistant.Characters.CharType
import com.greimul.pocketassistant.Characters.CharacterState
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Math.abs

class MainActivity : AppCompatActivity() {

    lateinit var charListAdapter:CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        charListAdapter = CharacterAdapter()

        val previewPx = resources.getDimension(R.dimen.viewpager2_preview)
        val pageMarginPx = resources.getDimension(R.dimen.viewpager2_page_margin)
        viewpager2_main.apply{
            adapter = charListAdapter
            clipToPadding = false
            offscreenPageLimit = 1
            setPageTransformer{
                    page,position->
                page.translationX = -(previewPx+pageMarginPx)*position
                page.scaleY = 1-(0.5f*abs(position))
            }
            registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    CharacterState.charType = CharacterState.charTypeList[position]
                }
            })
        }

        button_call_character.setOnClickListener {
            stopService(Intent(this,OverlayService::class.java))
            startService(Intent(this,OverlayService::class.java))
        }

    }
}