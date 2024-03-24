package io.travel_tunes.model.route

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class PointItemFullInfo(
    private val pointItemInfo: PointItemInfo,
    @RawRes private val audioRes: Int? = null,
    @DrawableRes private val pictureRes: Int? = null,
) : Parcelable {
    fun getTitle() = pointItemInfo.title
    fun getAudioRes() = audioRes
    fun getDrawableRes() = pictureRes
    fun getDescription() = pointItemInfo.getDescription()
}