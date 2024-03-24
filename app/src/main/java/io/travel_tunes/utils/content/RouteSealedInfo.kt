package io.travel_tunes.utils.content

import android.content.Context
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import io.travel_tunes.model.route.PointItemFullInfo
import io.travel_tunes.model.route.RouteItemInfo

sealed class RouteSealedInfo: Parcelable {
    @DrawableRes
    abstract fun getRoutePictureRes(): Int

    @RawRes
    abstract fun getRouteAudioRes(): Int?

    @DrawableRes
    abstract fun getPointPictureResList(): List<Int>

    @RawRes
    abstract fun getPointAudioResList(): List<Int>

    abstract fun getRouteItemInfo(context: Context): RouteItemInfo

    abstract fun getPointItemFullInfo(id: String): PointItemFullInfo?
}