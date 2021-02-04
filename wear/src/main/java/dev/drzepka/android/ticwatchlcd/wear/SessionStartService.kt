package dev.drzepka.android.ticwatchlcd.wear

import android.app.IntentService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class SessionStartService : IntentService("SessionStartService") {
    override fun onHandleIntent(intent: Intent?) {
        Log.i("Start", "session has been started")
    }

}
