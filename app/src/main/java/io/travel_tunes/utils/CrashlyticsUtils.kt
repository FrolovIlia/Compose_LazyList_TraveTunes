package io.travel_tunes.utils

import timber.log.Timber
class CrashlyticsUtils {
    companion object {
        fun sendThrowableNonFatal(e: Throwable) {
            Timber.e(e)
        }

        fun sendThrowableNonFatal(string: String) {
            if (string.isNotBlank()) {
                sendThrowableNonFatal(Throwable(string))
            }
        }
    }
}