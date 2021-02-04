package dev.drzepka.android.ticwatchlcd.phone

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.Status
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!PermissionManager.arePermissionsGranted(this))
            PermissionManager.requestPermissions(this, REQUEST_CODE_GENERAL_PERMISSIONS)
        else
            authorize()
    }

    override fun onStart() {
        super.onStart()
        Log.i("Main", "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Main", "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.i("Main", "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.i("Main", "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Main", "onDestroy()")
    }

    fun test(view: View) {
        //authorize()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_GOOGLE_FIT_PERMISSIONS) {
            if (resultCode == Activity.RESULT_OK) {
                access()
            } else {
                Toast.makeText(this, "No permissions", Toast.LENGTH_SHORT).show()
                if (data?.hasExtra("googleSignInStatus") == true) {
                    val status = data.getParcelableExtra<Status>("googleSignInStatus")
                    Log.e("MainActivity", "Google Fit permission result: $status")
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_GENERAL_PERMISSIONS) {
            PermissionManager.onPermissionRequestResult(
                permissions,
                grantResults
            )
            authorize()
        }
    }

    //https://developer.android.com/guide/topics/location/transitions
    private fun authorize() {
        // https://developers.google.com/fit/android/get-started#connect_to_the_fitness_service

        val options = getOptions()
        val account = GoogleSignIn.getAccountForExtension(this, options)
        if (!GoogleSignIn.hasPermissions(account, options)) {
            GoogleSignIn.requestPermissions(
                this,
                REQUEST_CODE_GOOGLE_FIT_PERMISSIONS,
                account,
                options
            )
        } else {
            access()
        }
    }

    private fun access() {
        Log.i("MainActivity", "Hooking to Google Fit")
        val intent = Intent(this, GoogleFitIntegrationService::class.java)
        startService(intent)
    }

    private fun getOptions(): FitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_ACTIVITY_SEGMENT)
        .addDataType(DataType.AGGREGATE_ACTIVITY_SUMMARY)
        .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.AGGREGATE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
        .build()


    companion object {
        private const val REQUEST_CODE_GENERAL_PERMISSIONS = 1
        private const val REQUEST_CODE_GOOGLE_FIT_PERMISSIONS = 2
    }
}
