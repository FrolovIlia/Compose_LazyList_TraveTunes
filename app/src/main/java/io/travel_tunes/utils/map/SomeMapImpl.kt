package io.travel_tunes.utils.map

import android.content.Context
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Polyline
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.collections.MarkerManager
import io.travel_tunes.model.route.PointItemInfo
import io.travel_tunes.model.route.RouteItemInfo
import io.travel_tunes.utils.extencions.addPolylineGeofence
import io.travel_tunes.utils.extencions.setMapStyle
import io.travel_tunes.utils.extencions.toLatLng
import io.travel_tunes.utils.extencions.toLatLngNew

class SomeMapImpl(private val googleMap: GoogleMap) : SomeMapInterface {

    private var pointsClusterManager: ClusterManager<PointItemInfo>? = null

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

    override fun setClusterManagers(
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

    override fun setOnMapClickListener(function: (LatLngNew?) -> Unit) {
        googleMap.setOnMapClickListener { function(it.toLatLngNew()) }
    }

    override fun centerMapAtPosition(position: LatLngNew, zoom: Float) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position.toLatLng(), zoom))
    }

    override fun updateRouteMarkers(context: Context, routeInfo: RouteItemInfo, mapPadding: Int) {
        val points = routeInfo.getPoints()
        pointsClusterManager?.apply {
            clearItems()
            addItems(points)
            cluster()
        }

        val pointPositions = routeInfo.getRoutePolyline()
        polylineShape?.remove()
        polylineShape = googleMap.addPolylineGeofence(pointPositions)

        val bounds = LatLngBounds.builder()
            .apply {
                pointPositions.map { position -> include(position) }
            }
            .build()
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, mapPadding))
    }
}