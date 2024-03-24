package io.travel_tunes.utils.map

import android.graphics.Bitmap
import androidx.annotation.DrawableRes

data class BitmapOrDrawableRes(
    val bitmap: Bitmap? = null,
    @DrawableRes val drawableRes: Int? = null
)