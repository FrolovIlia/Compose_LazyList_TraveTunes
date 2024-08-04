package io.travel_tunes.model.route

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PointItemFullInfo(
    @SerializedName("pointItemInfo") private val pointItemInfo: PointItemInfo,
    @SerializedName("audioRes") @RawRes private val audioRes: Int? = null,
    @SerializedName("pictureRes") @DrawableRes private val pictureRes: Int? = null,
) : Parcelable {
    fun getTitle() = pointItemInfo.title
    fun getAudioRes() = audioRes
    fun getDrawableRes() = pictureRes
    fun getDescription() = pointItemInfo.getDescription()
}