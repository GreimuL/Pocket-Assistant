package com.greimul.crawlertest

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_test.*
import kotlinx.android.synthetic.main.fragment_test.view.*

class Fragment1: Fragment(){
    private var mBound =  false
    private lateinit var mService:OverlayService
    private lateinit var alarmMgr:AlarmManager
    private lateinit var alarmIntent:PendingIntent
    private val connection = object: ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            mBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as OverlayService.LocalBind
            mService = binder.getService()
            mBound = true
            Log.d("istest","sadf")
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(activity?.baseContext,OverlayService::class.java).also{
            activity?.bindService(it,connection, Context.BIND_AUTO_CREATE)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_test,container,false)

        view.button_timer.setOnClickListener {

            alarmMgr.set(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 1000*5,
                alarmIntent
            )
            mService.setText("알람이 설정되었어요")
        }

        alarmMgr = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context,AlarmReceiver::class.java).let {
            PendingIntent.getBroadcast(context,0,it,0)
        }
        return view
    }
}