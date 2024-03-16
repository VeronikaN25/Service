package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val NOTIFICATION_ID=123
private const val NOTIFICATION_CHANNEL_ID="foreground_service"
class MyForegroundService: Service() {

    private lateinit var notification: Notification

    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        notification = createNotification("Start service")

        CoroutineScope(Dispatchers.IO)
            .launch {
                Log.d("TESTTEST","Start coroutine")
                for(item in 10 downTo 0){
                    Log.d("TESTTEST","Start for $item")
                    val updateNotification=createNotification(textMessage =" $item")
                    notificationManager.notify(NOTIFICATION_ID,updateNotification)
                    delay(1000L)
                }
                stopSelf()
            }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotification(textMessage:String):Notification{

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channel=NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_ID,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.notif)
            .setContentTitle("Timer")
            .setContentText(textMessage)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .build()

    }

    //intent системный обьект  , который указывает намеривание которое мы хотим выполнить
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


}