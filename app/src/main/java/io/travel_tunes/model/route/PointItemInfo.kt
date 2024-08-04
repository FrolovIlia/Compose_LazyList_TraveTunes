package io.travel_tunes.model.route

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import com.google.maps.android.clustering.ClusterItem
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class PointItemInfo(
    @SerializedName("id") private val id: String,
    @SerializedName("title") private val title: String,
    @SerializedName("description") private val description: String,
    @SerializedName("locationLat") private val locationLat: String,
    @SerializedName("locationLon") private val locationLon: String,
    @SerializedName("isEnabled") @IgnoredOnParcel private val isEnabled: Boolean = true,
    @SerializedName("isSelected") @IgnoredOnParcel private var isSelected: Boolean = false
) : Parcelable, ClusterItem {

    override fun getTitle() = title
    override fun getSnippet() = ""
    override fun getZIndex(): Float? = null
    override fun getPosition() = LatLng(locationLat.toDouble(), locationLon.toDouble())

    fun isEnabled() = isEnabled

    fun isSelected() = isSelected
    fun getId() = id
    fun getDescription() = description
}