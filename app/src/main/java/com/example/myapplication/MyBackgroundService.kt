package com.example.myapplication

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.util.Log
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat

const val CHANNEL_ID="BackgroundService"


class MyBackgroundService: IntentService("MyBackgroundService") {

    private val notificationId = 2
    override fun onHandleIntent(intent: Intent?) {
        monitorNetworkState()
    }
    private fun monitorNetworkState(){
        val connectivityManager=getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo

        if(networkInfo !=null && networkInfo.isConnected){
            showNotification("Network Connected","Network connection is established.")
        }else{
            showNotification("Network Disconnected","Network connection is lost.")
        }
    }

    private fun showNotification(title:String,context: String) {
       val notificationIntent=Intent(this,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val notification=NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(context)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()
        val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId,notification)
    }
}
