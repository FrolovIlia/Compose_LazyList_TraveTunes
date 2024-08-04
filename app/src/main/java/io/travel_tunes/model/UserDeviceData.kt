package io.travel_tunes.model

import android.os.Build
import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@IgnoreExtraProperties
data class UserDeviceData(
    @SerializedName("model") val model: String,
    @SerializedName("androidOS") val androidOS: String,
    @SerializedName("currentDateTime") val currentDateTime: String,
    @SerializedName("uniqueId") val uniqueId: String
) {
    companion object {
        fun getDeviceInfoModel() =
            "${Build.MANUFACTURER} ${Build.BRAND} ${Build.MODEL} ${Build.VERSION.INCREMENTAL}"

        fun getDeviceInfoOSVersion() = "${Build.VERSION.RELEASE} (${Build.VERSION.SDK_INT})"
        fun getCurrentDateTime(): String {
            val sdf = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault())
            val date = Date(System.currentTimeMillis())
            return sdf.format(date)
        }
    }
}