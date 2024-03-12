package io.travel_tunes.utils

import android.content.Context
import androidx.annotation.RawRes
import com.google.gson.Gson
import io.travel_tunes.model.route.RouteItemInfo
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.io.StringWriter
import java.io.Writer

fun parseJsonRes(context: Context, @RawRes rawId: Int): RouteItemInfo {
    val jsonString = getRawJsonAsString(context, rawId)
    return Gson().fromJson(jsonString, RouteItemInfo::class.java)
}

private fun getRawJsonAsString(context: Context, @RawRes rawId: Int): String {
    val inputStream: InputStream = context.resources.openRawResource(rawId)
    val writer: Writer = StringWriter()
    val buffer = CharArray(1024)
    inputStream.use { stream ->
        val reader: Reader = BufferedReader(InputStreamReader(stream, "UTF-8"))
        var n: Int
        while (reader.read(buffer).also { n = it } != -1) {
            writer.write(buffer, 0, n)
        }
    }
    return writer.toString()
}

