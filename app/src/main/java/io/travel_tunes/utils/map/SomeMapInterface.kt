package io.travel_tunes.utils.map

import android.content.Context
import androidx.annotation.RawRes
import io.travel_tunes.model.route.PointItemInfo
import io.travel_tunes.model.route.RouteItemInfo
import io.travel_tunes.utils.map.MapConstants.ZOOM_MAP_DEFAULT

interface SomeMapInterface {
    fun setUiSettings(
        context: Context,
        isMapToolbarEnabled: Boolean? = null,
        isCompassEnabled: Boolean? = null,
        isRotateGesturesEnabled: Boolean? = null,
        isMyLocationButtonEnabled: Boolean? = null,
        isZoomControlsEnabled: Boolean? = null,
        initZoom: Float = ZOOM_MAP_DEFAULT,
        initMapCenter: LatLngNew? = null,
        minZoomPreference: Float = 2.7F
    )

    fun setClusterManagers(
        context: Context,
        pointItemClickCallback: ((PointItemInfo) -> Unit)? = null
    )

    fun setOnMapClickListener(function: (LatLngNew?) -> Unit)

    fun updateRouteMarkers(context: Context, routeInfo: RouteItemInfo, mapPadding: Int)
    fun updateKml(context: Context, @RawRes kmlRes: Int, mapPadding: Int)

    fun centerMapAtPosition(
        position: LatLngNew,
        zoom: Float = ZOOM_MAP_DEFAULT
    )
}