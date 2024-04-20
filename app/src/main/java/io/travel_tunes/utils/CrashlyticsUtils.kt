package io.travel_tunes.utils

import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber
class CrashlyticsUtils {
    companion object {
        fun sendThrowableNonFatal(e: Throwable) {
            Timber.e(e)
            FirebaseCrashlytics.getInstance().recordException(e)
        }

        fun sendThrowableNonFatal(string: String) {
            if (string.isNotBlank()) {
                sendThrowableNonFatal(Throwable(string))
            }
        }
    }
}