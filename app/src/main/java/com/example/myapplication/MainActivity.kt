package com.example.myapplication

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.example.myapplication.databinding.ActivityMainBinding

private const val TAG="TESTTEST"

class MainActivity : AppCompatActivity() {

    private var binding:ActivityMainBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "Отработал onCreate")
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val intentBackground=Intent(this,MyBackgroundService::class.java)

        val intentForeground=Intent(this,MyForegroundService::class.java)

        binding?.let{
            it.btnForegroundStart.setOnClickListener {
                ContextCompat.startForegroundService(this, intentForeground)
            }

            it.btnForegroundStop.setOnClickListener {
                stopService(intentForeground)
            }
            it.btnBackgroundStart.setOnClickListener {
               startService(intentBackground)
            }
            it.btnBackgroundStop.setOnClickListener {
                stopService(intentBackground)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
    }


}