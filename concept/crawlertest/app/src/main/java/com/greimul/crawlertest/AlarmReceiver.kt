package com.greimul.crawlertest

import android.content.*
import android.os.IBinder
import android.util.Log

class AlarmReceiver: BroadcastReceiver() {
    lateinit var mService: OverlayService
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("ge","getAlarm")
        val binder = peekService(context,Intent(context,OverlayService::class.java)) as OverlayService.LocalBind
        mService = binder.getService()
        mService.setText("알람!")
    }
}