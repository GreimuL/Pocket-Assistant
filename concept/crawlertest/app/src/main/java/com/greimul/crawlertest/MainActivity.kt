package com.greimul.crawlertest

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.ActivityInfo
import android.graphics.PixelFormat
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_test.view.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    val PAGE_CNT = 3
    lateinit var animTest:AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewpager2_main.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(tablayout_main,viewpager2_main){
            tab, position ->
            tab.text = "$position"
        }.attach()
        startService(Intent(this,OverlayService::class.java))

    }

    private inner class ViewPagerAdapter(fa:FragmentActivity):FragmentStateAdapter(fa){
        override fun createFragment(position: Int): Fragment {
            return when(position){
                0->Fragment1()
                1->Fragment2()
                2->Fragment3()
                else-> Fragment3()
            }
        }

        override fun getItemCount() = PAGE_CNT
    }
}