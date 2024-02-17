package io.travel_tunes.data

import android.content.Context
import androidx.annotation.RawRes
import io.travel_tunes.utils.parseJsonRes


object DataGenerator {
    fun getDataFromJson(context: Context, @RawRes rawId: Int) = parseJsonRes(context, rawId)
}