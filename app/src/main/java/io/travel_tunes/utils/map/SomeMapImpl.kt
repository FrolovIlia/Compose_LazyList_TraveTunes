package io.travel_tunes.utils.map

import android.content.Context
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import io.travel_tunes.R
import io.travel_tunes.model.route.RouteItemInfo
import io.travel_tunes.utils.bitmapIconFromVector
import io.travel_tunes.utils.extencions.addPolylineGeofence
import io.travel_tunes.utils.extencions.setMapStyle
import io.travel_tunes.utils.extencions.toLatLng

class SomeMapImpl(private val googleMap: GoogleMap) : SomeMapInterface {

    private val markers = mutableListOf<Marker>()
    private var polylineShape: Polyline? = null

    override fun setUiSettings(
        context: Context,
        isMapToolbarEnabled: Boolean?,
        isCompassEnabled: Boolean?,
        isRotateGesturesEnabled: Boolean?,
        isMyLocationButtonEnabled: Boolean?,
        isZoomControlsEnabled: Boolean?,
        initZoom: Float,
        initMapCenter: LatLngNew?,
        minZoomPreference: Float
    ) {
        googleMap.uiSettings.apply {
            isMapToolbarEnabled?.let {
                this.isMapToolbarEnabled = isMapToolbarEnabled
            }
            isCompassEnabled?.let {
                this.isCompassEnabled = isCompassEnabled
            }
            isRotateGesturesEnabled?.let {
                this.isRotateGesturesEnabled = isRotateGesturesEnabled
            }
            isMyLocationButtonEnabled?.let {
                this.isMyLocationButtonEnabled = isMyLocationButtonEnabled
            }
            isZoomControlsEnabled?.let {
                this.isZoomControlsEnabled = isZoomControlsEnabled
            }

            setAllGesturesEnabled(true)
        }

        googleMap.apply {
            setMapStyle(context)
            setMinZoomPreference(minZoomPreference)
            initMapCenter?.let {
                centerMapAtPosition(it, initZoom)
            }
        }
    }

    override fun centerMapAtPosition(position: LatLngNew, zoom: Float) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position.toLatLng(), zoom))
    }

    override fun addRouteMarkers(context: Context, routeInfo: RouteItemInfo, mapPadding: Int) {
        val points = routeInfo.getPoints()
        points.forEach { point ->
            val iconBitmap = bitmapIconFromVector(context, R.drawable.ic_points)
            val icon = if (iconBitmap != null) {
                BitmapDescriptorFactory.fromBitmap(iconBitmap)
            } else {
                BitmapDescriptorFactory.defaultMarker()
            }
            googleMap.addMarker(
                MarkerOptions()
                    .position(point.getPosition())
                    .icon(icon)
            )?.let { newMarker ->
                newMarker.tag = point
                markers.add(newMarker)
            }
        }

        val pointPositions = routeInfo.getRoutePolyline()
        polylineShape = googleMap.addPolylineGeofence(pointPositions)

        val bounds = LatLngBounds.builder()
            .apply {
                pointPositions.map { position -> include(position) }
            }
            .build()
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, mapPadding))
    }
}