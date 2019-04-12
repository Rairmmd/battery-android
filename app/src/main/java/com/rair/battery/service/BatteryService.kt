package com.rair.battery.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.PixelFormat
import android.graphics.drawable.AnimationDrawable
import android.net.TrafficStats
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import com.rair.battery.R

/**
 * @author Rair
 * @date 2019/4/12
 *
 * desc:
 */
class BatteryService : Service(), Handler.Callback {
    private val GO_HOME: Int = 1000

    private val GO_PREVIEW: Int = 1001
    private val TIME: Long = 500
    /**
     * 绘制动画对象
     */
    private var mAnimationDrawable: AnimationDrawable? = null

    private var mImages: ImageView? = null
    private var isFirstRun: Boolean = false

    private var mWindowManager: WindowManager? = null

    private var mWindowParams: WindowManager.LayoutParams? = null
    private var mLinearLayout: LinearLayout? = null

    private var mTotalData: Long = TrafficStats.getTotalRxBytes()

    private var mTranferData: Long = TrafficStats.getTotalTxBytes()
    private var mHandler: Handler? = null

    private var mCount: Int = 0

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createdFloatWindow()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createdFloatWindow() {
        mWindowParams = WindowManager.LayoutParams()
        mWindowManager = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mWindowParams!!.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            mWindowParams!!.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR
        }
        mWindowParams!!.format = PixelFormat.RGBA_8888
        mWindowParams!!.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE and
                WindowManager.LayoutParams.FLAG_FULLSCREEN and WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        mWindowParams!!.gravity = Gravity.START and Gravity.TOP

        mWindowParams!!.x = 500
        mWindowParams!!.y = 500
        mWindowParams!!.width = WindowManager.LayoutParams.WRAP_CONTENT
        mWindowParams!!.height = WindowManager.LayoutParams.WRAP_CONTENT

        val layoutInflater = LayoutInflater.from(application)
        mLinearLayout = layoutInflater.inflate(R.layout.layout_float_view, LinearLayout(this), false) as LinearLayout?
        mWindowManager?.addView(mLinearLayout, mWindowParams)
        mLinearLayout?.setOnLongClickListener {
            val sharedPreferences = getSharedPreferences("def", MODE_PRIVATE) as SharedPreferences
            isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)
            if (!isFirstRun) {
                mHandler?.sendEmptyMessageDelayed(GO_HOME, TIME)
            }
            true
        }
        mLinearLayout?.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )


    }

    override fun handleMessage(msg: Message?): Boolean {
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mLinearLayout != null) {
            mWindowManager?.removeView(mLinearLayout)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}