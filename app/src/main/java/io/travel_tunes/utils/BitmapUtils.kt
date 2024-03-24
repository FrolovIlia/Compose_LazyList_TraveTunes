package io.travel_tunes.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat

fun bitmapIconFromVector(context: Context?, vectorResId: Int): Bitmap? {
    if (context == null) return null
    ContextCompat.getDrawable(context, vectorResId)?.let {
        it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
        val bitmap =
            Bitmap.createBitmap(it.intrinsicWidth, it.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        it.draw(canvas)
        return bitmap
    }.run {
        return null
    }
}