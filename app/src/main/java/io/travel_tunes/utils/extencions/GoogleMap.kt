package io.travel_tunes.utils.extencions

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import io.travel_tunes.R
import io.travel_tunes.utils.CrashlyticsUtils
import io.travel_tunes.utils.map.LatLngNew

fun GoogleMap.setMapStyle(context: Context, jsonResourceId: Int = R.raw.style_json) {
    try {
        val success: Boolean = this.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(context, jsonResourceId)
        )
        if (!success) {
            CrashlyticsUtils.sendThrowableNonFatal("Style parsing failed.")
        }
    } catch (exception: Resources.NotFoundException) {
        CrashlyticsUtils.sendThrowableNonFatal(exception)
    }
}

fun GoogleMap.addPolylineGeofence(positions: List<LatLng>): Polyline? {
    return if (positions.isNotEmpty()) {
        val mapPoints = positions.toTypedArray()
        val color = Color.BLUE
        val polylineOptions = PolylineOptions()
            .add(*mapPoints)
            .color(color)
        val polyline = addPolyline(polylineOptions)
        polyline
    } else null
}

fun LatLngNew.toLatLng(): LatLng = LatLng(latitude, longitude)