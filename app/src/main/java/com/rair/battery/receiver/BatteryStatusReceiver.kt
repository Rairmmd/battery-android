package com.rair.battery.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import com.rair.battery.constant.EventType
import com.rair.battery.event.AppEvent
import com.rair.battery.service.BatteryService
import org.greenrobot.eventbus.EventBus

/**
 * @author Rair
 * @date 2019/4/12
 *
 * desc:
 */
class BatteryStatusReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && intent.action.equals(Intent.ACTION_BATTERY_CHANGED, true)) {
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100)
            val mStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN)
            val mPlugType = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0)
            val mLevel = 100 * level / scale
            when (mStatus) {
                BatteryManager.BATTERY_STATUS_CHARGING -> {
                    if (mPlugType == BatteryManager.BATTERY_PLUGGED_AC) {

                    } else if (mPlugType == BatteryManager.BATTERY_PLUGGED_USB) {

                    }
                    context?.startService(Intent(context, BatteryService().javaClass))
                }
                BatteryManager.BATTERY_STATUS_FULL -> {
                    if (mPlugType == BatteryManager.BATTERY_PLUGGED_AC) {

                    } else if (mPlugType == BatteryManager.BATTERY_PLUGGED_USB) {

                    }
                }
                BatteryManager.BATTERY_STATUS_NOT_CHARGING -> {

                }
                BatteryManager.BATTERY_STATUS_DISCHARGING -> {
                    context?.stopService(Intent(context, BatteryService().javaClass))
                }
            }
            EventBus.getDefault().post(AppEvent(EventType.TYPE_LEVEL, mLevel))
        }
    }
}
