package io.travel_tunes.model.route

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class PointItemInfo(
    private val id: String,
    private val title: String,
    private val description: String,
    private val locationLat: String,
    private val locationLon: String,
    @IgnoredOnParcel private var isSelected: Boolean = false
) : Parcelable, ClusterItem {

    override fun getTitle() = title
    override fun getSnippet() = ""
    override fun getZIndex(): Float? = null
    override fun getPosition() = LatLng(locationLat.toDouble(), locationLon.toDouble())

    fun isSelected() = isSelected
    fun getId() = id
    fun getDescription() = description
}