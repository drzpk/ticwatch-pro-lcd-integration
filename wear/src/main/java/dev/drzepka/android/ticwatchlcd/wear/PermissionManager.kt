package dev.drzepka.android.ticwatchlcd.wear

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionManager {

    private val REQUIRED_PERMISSIONS = listOf(
        Manifest.permission.ACTIVITY_RECOGNITION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.BODY_SENSORS
    )

    fun arePermissionsGranted(context: Context): Boolean {
        return REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun requestPermissions(activity: Activity, requestCode: Int) {
        val notGrantedPermissions = REQUIRED_PERMISSIONS
            .filter {
                ContextCompat.checkSelfPermission(
                    activity,
                    it
                ) == PackageManager.PERMISSION_DENIED
            }

        Log.i("PermissionManager", "Not granted permissions: $notGrantedPermissions")

        val permanentlyDenied =
            notGrantedPermissions.filter { !activity.shouldShowRequestPermissionRationale(it) }
        if (permanentlyDenied.isNotEmpty()) {
            Log.e("PermissionManager", "Some permissions are permanently denied")
            // TODO display error to user
        }

        ActivityCompat.requestPermissions(
            activity, notGrantedPermissions.toTypedArray(), requestCode
        )
    }

    fun onPermissionRequestResult(permissions: Array<out String>, grantResults: IntArray) {
        Log.i("test", "nothing here yet")
    }
}