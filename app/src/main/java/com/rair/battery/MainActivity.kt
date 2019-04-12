package com.rair.battery

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rair.battery.receiver.BatteryStatusReceiver

class MainActivity : AppCompatActivity() {

    private var batteryLevel: Int = 0
    private var receiver: BatteryStatusReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val windowWidth = windowManager.defaultDisplay.width
        receiver = BatteryStatusReceiver()
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(receiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

}
