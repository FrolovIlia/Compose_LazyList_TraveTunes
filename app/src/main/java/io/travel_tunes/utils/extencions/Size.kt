package io.travel_tunes.utils.extencions

import android.content.Context
import android.util.TypedValue

fun Int.toDp(context: Context) : Int {
    if (this < 0) return 0
    val resources = context.resources
    val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), resources.displayMetrics)
    return px.toInt()
}

fun Int.toDpFloat(context: Context) : Float {
    if (this < 0) return 0F
    val resources = context.resources
    val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), resources.displayMetrics)
    return px.toFloat()
}