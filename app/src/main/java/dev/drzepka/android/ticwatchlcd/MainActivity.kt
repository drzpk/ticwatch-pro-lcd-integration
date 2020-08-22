package dev.drzepka.android.ticwatchlcd

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import dev.drzepka.android.ticwatchlcd.ticwatch.McuServiceConnection

class MainActivity : WearableActivity() {

    private var service: McuServiceConnection? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPause() {
        super.onPause()
        if (service != null)
            unbindService(service!!)
    }

    @Suppress("UNUSED_PARAMETER")
    fun bindToService(view: View) {
        if (service != null)
            return

        val intent = Intent("com.mobvoi.wear.action.BIND_MCU")
        val services: List<ResolveInfo>? = packageManager.queryIntentServices(intent, 0)
        if (services == null || services.isEmpty()) {
            Toast.makeText(this, "MCU service is not available", Toast.LENGTH_SHORT).show()
            Log.i(MainActivity::class.java.canonicalName, "MCU service is not available")
            return
        }

        val name = getComponentName(services)
        if (name == null) {
            Log.i(MainActivity::class.java.canonicalName, "No component found")
            return
        }

        intent.component = name


        val connection = McuServiceConnection()
        val result = bindService(intent, connection, Context.BIND_AUTO_CREATE)
        if (!result) {
            Log.e(MainActivity::class.java.canonicalName, "Service bind unsuccessful")
        } else {
            service = connection
        }
    }

    private fun getComponentName(services: List<ResolveInfo>): ComponentName? {
        for (service in services) {
            val info = service.serviceInfo
            val flags = info.applicationInfo.flags
            if (flags.and(ApplicationInfo.FLAG_SYSTEM) == 0) continue

            return ComponentName(info.packageName, info.name)
        }

        return null
    }
}
