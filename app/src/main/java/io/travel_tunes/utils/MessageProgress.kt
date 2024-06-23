package io.travel_tunes.utils

import android.content.Context
import androidx.annotation.StringRes

data class MessageProgress(
    private val textMessage: String?,
    @StringRes private val resStringInt: Int
) {
    fun getText(context: Context?): String {
        return when {
            !textMessage.isNullOrEmpty() -> textMessage
            context == null -> ""
            else -> {
                context.resources.getString(resStringInt)
            }
        }
    }
}