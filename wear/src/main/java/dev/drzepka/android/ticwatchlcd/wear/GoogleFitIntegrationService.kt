package dev.drzepka.android.ticwatchlcd.wear

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType

class GoogleFitIntegrationService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("Service", "onStart()")
        access()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Service", "onDestroy()")
    }

    private fun access() {
        Log.i("MainActivity", "Hooking to Google Fit")
        val intent = Intent(this, SessionStartService::class.java)
        val pendingIntent = PendingIntent.getService(this, 0, intent, 0)
        val accountForExtension = GoogleSignIn.getAccountForExtension(this, getOptions())

        // todo: this doesn't work on Wear OS
        // WORKS ON A PHONE
        Fitness.getSessionsClient(this, accountForExtension)
            .registerForSessions(pendingIntent)
    }

    private fun getOptions(): FitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_ACTIVITY_SEGMENT)
        .addDataType(DataType.AGGREGATE_ACTIVITY_SUMMARY)
        .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.AGGREGATE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
        .build()
}