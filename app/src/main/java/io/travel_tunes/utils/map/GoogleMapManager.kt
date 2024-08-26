package io.travel_tunes.utils.map

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Polyline
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.collections.MarkerManager
import io.travel_tunes.model.route.PointItemInfo
import io.travel_tunes.model.route.RouteItemInfo
import io.travel_tunes.utils.CrashlyticsUtils
import io.travel_tunes.utils.extencions.addPolylineLatLng
import io.travel_tunes.utils.extencions.isLocationPermissionFineGranted
import io.travel_tunes.utils.extencions.setMapStyle
import io.travel_tunes.utils.extencions.toLatLng
import io.travel_tunes.utils.extencions.toLatLngNew

class GoogleMapManager(private val googleMap: GoogleMap) {

    private var pointsClusterManager: ClusterManager<PointItemInfo>? = null

    private var polylineShape: Polyline? = null

    fun setUiSettings(
        context: Context,
        isMapToolbarEnabled: Boolean?,
        isCompassEnabled: Boolean?,
        isRotateGesturesEnabled: Boolean?,
        isMyLocationButtonEnabled: Boolean?,
        isZoomControlsEnabled: Boolean?,
        initZoom: Float = MapConstants.ZOOM_MAP_DEFAULT,
        initMapCenter: LatLngNew? = null,
        minZoomPreference: Float = 2.7F,
    ) {
        googleMap.uiSettings.apply {
            this.isMapToolbarEnabled = isMapToolbarEnabled ?: false
            this.isCompassEnabled = isCompassEnabled ?: false
            this.isRotateGesturesEnabled = isRotateGesturesEnabled ?: false
            this.isMyLocationButtonEnabled = isMyLocationButtonEnabled ?: false
            this.isZoomControlsEnabled = isZoomControlsEnabled ?: false

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

    fun setClusterManagers(
        context: Context,
        pointItemClickCallback: ((PointItemInfo) -> Unit)?
    ) {
        val markerManager = MarkerManager(googleMap)
        pointsClusterManager = ClusterManager(context, googleMap, markerManager)
        pointsClusterManager?.apply {
            renderer = PointMarkersRenderer(context, googleMap, this)
            pointItemClickCallback?.let { callback ->
                setOnClusterItemClickListener { point ->
                    callback.invoke(point)
                    false
                }
            }
        }
        googleMap.setOnCameraIdleListener(pointsClusterManager)
    }

    fun setOnMapClickListener(function: (LatLngNew?) -> Unit) {
        googleMap.setOnMapClickListener { function(it.toLatLngNew()) }
    }

    private fun centerMapAtPosition(position: LatLngNew, zoom: Float = MapConstants.ZOOM_MAP_DEFAULT) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position.toLatLng(), zoom))
    }

    fun setIsMyLocationEnabled(isEnabled: Boolean, activity: Activity) {
        try {
            val correctIsEnabled = activity.isLocationPermissionFineGranted() && isEnabled
            @SuppressLint("MissingPermission")
            googleMap.isMyLocationEnabled = correctIsEnabled
        } catch (e: Exception) {
            CrashlyticsUtils.sendThrowableNonFatal(e)
        }
    }

    fun initMyLocation(latitude: Double, longitude: Double, isNeedCenterMap: Boolean) {
        if (isNeedCenterMap) {
            centerMapAtPosition(LatLngNew(latitude, longitude))
        }
    }

    fun updateRouteMarkers(routeInfo: RouteItemInfo, mapPadding: Int) {
        val points = routeInfo.getPoints()
        val isFirstTime = pointsClusterManager?.algorithm?.items.isNullOrEmpty()
        pointsClusterManager?.apply {
            clearItems()
            addItems(points)
            cluster()
        }

        if (isFirstTime) {
            val bounds = LatLngBounds.builder()
                .apply {
                    include(routeInfo.getNortheast().toLatLng())
                    include(routeInfo.getSouthwest().toLatLng())
                }
                .build()
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, mapPadding))
        }
    }

    fun updatePolygon(routeMapPoints: List<LatLngNew>) {
        polylineShape?.remove()
        polylineShape =
            googleMap.addPolylineLatLng(positions = routeMapPoints.map { it.toLatLng() })
    }
}