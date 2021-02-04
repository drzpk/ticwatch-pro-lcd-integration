package dev.drzepka.android.ticwatchlcd.wear.ticwatch

import android.os.IBinder
import android.os.Parcel

class McuManager(private val binder: IBinder) {

    fun sendData(data: FitnessMcuData?) {
        val dataParcel = Parcel.obtain()
        val replyParcel = Parcel.obtain()

        try {
            dataParcel.writeInterfaceToken(MANAGER_PACKAGE)

            if (data == null) {
                dataParcel.writeInt(0)
            } else {
                dataParcel.writeInt(1)
                data.writeToParcel(dataParcel, 0)
            }

            binder.transact(1, dataParcel, replyParcel, 0)
            replyParcel.readException()
        } finally {
            dataParcel.recycle()
            replyParcel.recycle()
        }
    }

    companion object {
        private const val MANAGER_PACKAGE = "com.mobvoi.wear.mcu.IMcuManager"
    }
}