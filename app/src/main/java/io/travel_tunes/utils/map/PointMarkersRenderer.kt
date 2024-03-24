package io.travel_tunes.utils.map

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import io.travel_tunes.model.route.PointItemInfo

class PointMarkersRenderer(
    private val context: Context,
    private val googleMap: GoogleMap,
    clusterManager: ClusterManager<PointItemInfo>
) : DefaultClusterRenderer<PointItemInfo>(
    context,
    googleMap,
    clusterManager
) {

    override fun onBeforeClusterItemRendered(
        pointItemInfo: PointItemInfo,
        markerOptions: MarkerOptions
    ) {
        val newZoom = googleMap.cameraPosition.zoom
        markerOptions.apply {
            icon(getIconForPointMarker(pointItemInfo, newZoom))
        }
    }

    override fun onClusterItemUpdated(pointItemInfo: PointItemInfo, marker: Marker) {
        marker.tag = pointItemInfo
        val newZoom = googleMap.cameraPosition.zoom
        marker.apply {
            setIcon(getIconForPointMarker(pointItemInfo, newZoom))
        }
    }

    private fun getIconForPointMarker(
        pointItemInfo: PointItemInfo,
        newZoom: Float
    ): BitmapDescriptor {
        return getPointItemMarkerBitmap(
            context = context,
            pointItemInfo = pointItemInfo,
            zoom = newZoom
        )
    }

    override fun shouldRenderAsCluster(cluster: Cluster<PointItemInfo>): Boolean {
        return false
    }

}