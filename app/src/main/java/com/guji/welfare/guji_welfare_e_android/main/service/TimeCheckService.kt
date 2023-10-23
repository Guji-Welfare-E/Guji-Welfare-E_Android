package com.guji.welfare.guji_welfare_e_android.main.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.telephony.SmsManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.guji.welfare.guji_welfare_e_android.App
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.main.screen.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimeCheckService : Service() {
    private var isServiceRunning = false
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(!isServiceRunning){
            isServiceRunning = true
            val notification = createNotification()
            startForeground(1, notification)
            checkTime()
        }

        return START_STICKY
    }

    private fun sendMessage() {
        CoroutineScope(Dispatchers.IO).launch {
            val smsManager: SmsManager = SmsManager.getDefault()
            Log.d("상태", "메시지 보내기")
            smsManager.sendTextMessage(
                App.prefs.welfareWorkerPhoneNumber,
                null,
                "건록이가 쓰러진거같네요 2-3반으로 신속히 와주세요",
                null,
                null
            )
        }
    }

    private fun checkTime() {
        var time = 0
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                if (!isScreenOn()) time++
                else if (time >= 43200) {sendMessage(); time=0}
                else time = 0
                delay(1000L)
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onDestroy() {
        Log.d("상태", "onDestroy")
        stopForeground(STOP_FOREGROUND_DETACH)
        isServiceRunning = false
        super.onDestroy()
    }

    private fun isScreenOn(): Boolean {
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        return powerManager.isInteractive
    }

    private fun createNotification(): Notification {
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("my_service", "My Background Service")
            } else {
                ""
            }

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Background Service")
            .setContentText("앱이 백그라운드에서 실행 중입니다.")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .build() // NotificationCompat.Builder에서 build()를 호출하여 Notification 객체를 얻습니다.
    }

    private fun createNotificationChannel(channelId: String, channelName: String): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
            return channelId
        }
        return ""
    }

}