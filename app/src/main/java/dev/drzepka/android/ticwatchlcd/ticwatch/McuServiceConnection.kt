package dev.drzepka.android.ticwatchlcd.ticwatch

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log

// .class Lcom/mobvoi/fitness/service/i0$a;
class McuServiceConnection : ServiceConnection {

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        Log.i("McuServiceConnection", "service connected")
        val manager = McuManager(service!!)
        Handler(Looper.getMainLooper()).postDelayed({
            Log.i("McuServiceConnection", "Sending data to MCU")
            val data = FitnessMcuData()
            // 1 - running
            data.type = 1
            data.calorie = 150
            data.durationMs = 5000
            data.speed = 12.34f
            data.isKilometer = true

            data.distance = 1.34f

            manager.sendData(data)

            // Return to normal LCD state
            Handler(Looper.getMainLooper()).postDelayed({
                val data2 = FitnessMcuData()
                data2.type = 0
                manager.sendData(data2)
            }, 13000)
        }, 3000)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        Log.i("McuServiceConnection", "service disconnected")
    }
}