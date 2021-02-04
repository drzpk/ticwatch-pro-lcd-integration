package dev.drzepka.android.ticwatchlcd.wear.ticwatch

import android.os.Parcel
import android.os.Parcelable

/**
 * Exact copy of class from the TicHealth app that is used to transfer fitness data to LCD display.
 *
 *
 */
class FitnessMcuData() : Parcelable {

    /**
     * Possible types:
     * indoor_cycling
     * outdoor_cycling
     * rowing_machine
     * swimming
     */
    var type: Int = 0
    var state: Int = 0
    var heartRate: Int = 0
    var durationMs: Long = 0L
    var lastUpdateTime: Long = 0L
    var calorie: Int = 0
    var distance: Float = 0f
    var speed: Float = 0f
    var pace: Int = 0
    var gpsStatus: Int = 0
    var isKilometer: Boolean = false

    @Deprecated("")
    private var f: Int = 0

    constructor(parcel: Parcel) : this() {
        type = parcel.readInt()
        state = parcel.readInt()
        heartRate = parcel.readInt()
        f = parcel.readInt()
        calorie = parcel.readInt()
        distance = parcel.readFloat()
        speed = parcel.readFloat()
        pace = parcel.readInt()
        gpsStatus = parcel.readInt()
        isKilometer = parcel.readInt() == 1
        durationMs = parcel.readLong()
        lastUpdateTime = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(type)
        parcel.writeInt(state)
        parcel.writeInt(heartRate)
        parcel.writeInt(f)
        parcel.writeInt(calorie)
        parcel.writeFloat(distance)
        parcel.writeFloat(speed)
        parcel.writeInt(pace)
        parcel.writeInt(gpsStatus)
        parcel.writeInt(if (isKilometer) 1 else 0)
        parcel.writeLong(durationMs)
        parcel.writeLong(lastUpdateTime)
    }

    override fun describeContents(): Int = 0

    override fun toString(): String =
        """FitnessMcuData ${hashCode()} {" +
                |type = $type,
                |state = $state,
                |heartRate = $heartRate,
                |duration = $durationMs,
                |calorie = $calorie,
                |distance = $distance,
                |speed = $speed,
                |pace = $pace,
                |gpsStatus = $gpsStatus,
                |isKilometer = $isKilometer,
                |lastUpdateTime = $lastUpdateTime
                |}""".trimMargin()


    companion object CREATOR : Parcelable.Creator<FitnessMcuData> {
        override fun createFromParcel(parcel: Parcel): FitnessMcuData {
            return FitnessMcuData(parcel)
        }

        override fun newArray(size: Int): Array<FitnessMcuData?> {
            return arrayOfNulls(size)
        }
    }

}