package io.travel_tunes.utils.content

import android.content.Context
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import io.travel_tunes.model.route.PointItemFullInfo
import io.travel_tunes.model.route.RouteItemInfo

sealed class RouteSealedInfo : Parcelable {

    private var isPaid: Boolean = false

    /**
     * количество доступных для ознакомления с маршрутом точек
     */
    open fun getCountFreePoints(): Int = 5

    abstract fun getRouteTag(): String

    fun setIsPaid(isPaid: Boolean) {
        this.isPaid = isPaid
    }

    fun isRoutePaid() = isPaid

    @RawRes
    abstract fun getRouteKml(): Int

    /**
     * изображение для экрана со списком маршрутов
     */
    @DrawableRes
    abstract fun getRouteMainPictureRes(): Int

    /**
     * изображение для экрана информации о маршруте
     */
    @DrawableRes
    abstract fun getRouteDescriptionPictureRes(): Int

    @RawRes
    abstract fun getRouteAudioRes(): Int?

    @DrawableRes
    abstract fun getPointPictureResList(): List<Int>

    @RawRes
    abstract fun getPointAudioResList(): List<Int>

    abstract fun getRouteItemInfo(context: Context): RouteItemInfo

    abstract fun getPointItemFullInfo(id: String): PointItemFullInfo?
}