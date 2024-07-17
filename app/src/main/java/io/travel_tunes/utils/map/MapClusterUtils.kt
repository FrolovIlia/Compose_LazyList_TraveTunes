package io.travel_tunes.utils.map

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.ui.IconGenerator
import io.travel_tunes.R
import io.travel_tunes.model.route.PointItemInfo
import io.travel_tunes.utils.bitmapIconFromVector
import io.travel_tunes.utils.extencions.changeText
import io.travel_tunes.utils.getBitmapFromView

fun getPointItemMarkerBitmap(
    context: Context,
    pointItemInfo: PointItemInfo,
    zoom: Float = MapConstants.ZOOM_MAP_DEFAULT
): BitmapDescriptor {
    val bitmapOrDrawableRes =
        getPointMarkerBitmapOrDrawableRes(context, pointItemInfo, zoom)
    return when {
        bitmapOrDrawableRes.bitmap != null -> {
            val generator = IconGenerator(context)
            generator.setBackground(BitmapDrawable(context.resources, bitmapOrDrawableRes.bitmap))
            BitmapDescriptorFactory.fromBitmap(generator.makeIcon())
        }

        bitmapOrDrawableRes.drawableRes != null -> {
            bitmapDescriptorFromVector(context, bitmapOrDrawableRes.drawableRes)
        }

        else -> throw Exception("bitmapOrDrawableRes must contain not null field")
    }
}

fun getPointMarkerBitmapOrDrawableRes(
    context: Context,
    pointItemInfo: PointItemInfo,
    zoom: Float
): BitmapOrDrawableRes {
    val layoutId = when {
        !pointItemInfo.isEnabled() -> {
            R.layout.marker_disabled_circle_layout
        }

        pointItemInfo.isSelected() -> {
            R.layout.marker_selected_circle_layout
        }

        else -> {
            R.layout.marker_default_circle_layout
        }
    }
    val clusterView =
        LayoutInflater.from(context)
            .inflate(layoutId, null)
    (clusterView.findViewById<View>(R.id.text) as TextView).changeText(pointItemInfo.getId())
    return BitmapOrDrawableRes(bitmap = getBitmapFromView(clusterView))
}

fun bitmapDescriptorFromVector(context: Context?, vectorResId: Int): BitmapDescriptor {
    if (context == null) return BitmapDescriptorFactory.defaultMarker()
    val bitmap = bitmapIconFromVector(context, vectorResId)
    return if (bitmap == null)
        BitmapDescriptorFactory.defaultMarker()
    else
        BitmapDescriptorFactory.fromBitmap(bitmap)
}