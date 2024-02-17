package io.travel_tunes.data

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import io.travel_tunes.R
import io.travel_tunes.utils.parseJsonRes


object DataGenerator {
    fun getDataFromJson(context: Context, @RawRes rawId: Int) = parseJsonRes(context, rawId)

    fun getAudiosByRouteTag(routeTag: String): Map<String, Int> {
        return when (routeTag) {
            "ivanovo_1" -> {
                mapOf(
                    "1" to R.raw.route_ivanovo_1_1,
                    "2" to R.raw.route_ivanovo_1_2,
                    "3" to R.raw.route_ivanovo_1_3,
                    "4" to R.raw.route_ivanovo_1_4,
                    "5" to R.raw.route_ivanovo_1_5,
                    "6" to R.raw.route_ivanovo_1_6,
                    "7" to R.raw.route_ivanovo_1_7,
                    "8" to R.raw.route_ivanovo_1_8,
                    "9" to R.raw.route_ivanovo_1_9,
                    "10" to R.raw.route_ivanovo_1_10,
                    "11" to R.raw.route_ivanovo_1_11,
                    "12" to R.raw.route_ivanovo_1_12,
                    "13" to R.raw.route_ivanovo_1_13,
                    "14" to R.raw.route_ivanovo_1_14,
                    "15" to R.raw.route_ivanovo_1_15,
                    "16" to R.raw.route_ivanovo_1_16,
                    "17" to R.raw.route_ivanovo_1_17,
                    "18" to R.raw.route_ivanovo_1_18,
                    "19" to R.raw.route_ivanovo_1_19,
                    "20" to R.raw.route_ivanovo_1_20,
                    "21" to R.raw.route_ivanovo_1_21,
                    "22" to R.raw.route_ivanovo_1_22,
                    "23" to R.raw.route_ivanovo_1_23,
                    "24" to R.raw.route_ivanovo_1_24,
                    "25" to R.raw.route_ivanovo_1_25,
                    "26" to R.raw.route_ivanovo_1_26,
                    "27" to R.raw.route_ivanovo_1_27,
                    "28" to R.raw.route_ivanovo_1_28,
                    "29" to R.raw.route_ivanovo_1_29,
                    "30" to R.raw.route_ivanovo_1_30,
                    "31" to R.raw.route_ivanovo_1_31,
                    "32" to R.raw.route_ivanovo_1_32,
                )
            }

            else -> emptyMap()
        }
    }

    /**
     * get picture for route
     */
    @DrawableRes
    fun getRoutePictureResByRouteTag(routeTag: String): Int {
        val pictureRes = when (routeTag) {
            "ivanovo_1" -> R.drawable.pic_ivanovo_city
            else -> R.drawable.pic_default
        }
        return pictureRes
    }

    /**
     * get pictures for route's pounts
     */
    fun getPointPicturesResByRouteTag(routeTag: String) = when (routeTag) {
        "ivanovo_1" -> {
            listOf(
                R.drawable.route_ivanovo_1_1,
                R.drawable.route_ivanovo_1_2,
                R.drawable.route_ivanovo_1_3,
                R.drawable.route_ivanovo_1_4,
                R.drawable.route_ivanovo_1_5,
                R.drawable.route_ivanovo_1_6,
                R.drawable.route_ivanovo_1_7,
                R.drawable.route_ivanovo_1_8,
                R.drawable.route_ivanovo_1_9,
                R.drawable.route_ivanovo_1_10,
                R.drawable.route_ivanovo_1_11,
                R.drawable.route_ivanovo_1_12,
                R.drawable.route_ivanovo_1_13,
                R.drawable.route_ivanovo_1_14,
                R.drawable.route_ivanovo_1_15,
                R.drawable.route_ivanovo_1_16,
                R.drawable.route_ivanovo_1_17,
                R.drawable.route_ivanovo_1_18,
                R.drawable.route_ivanovo_1_19,
                R.drawable.route_ivanovo_1_20,
                R.drawable.route_ivanovo_1_21,
                R.drawable.route_ivanovo_1_22,
                R.drawable.route_ivanovo_1_23,
                R.drawable.route_ivanovo_1_24,
                R.drawable.route_ivanovo_1_25,
                R.drawable.route_ivanovo_1_26,
                R.drawable.route_ivanovo_1_27,
                R.drawable.route_ivanovo_1_28,
                R.drawable.route_ivanovo_1_29,
                R.drawable.route_ivanovo_1_30,
                R.drawable.route_ivanovo_1_31,
                R.drawable.route_ivanovo_1_32
            )
        }

        else -> emptyList()
    }

}