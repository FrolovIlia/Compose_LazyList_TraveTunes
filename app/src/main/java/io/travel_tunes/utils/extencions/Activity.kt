package io.travel_tunes.utils.extencions

import android.app.Activity
import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.PersistableBundle
import android.widget.Toast

fun Activity.copyToClipboard(
    label: String,
    textForCopy: String,
    textForToast: String,
    isTextSensitive: Boolean = true
) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, textForCopy)
    clip.description.extras = PersistableBundle().apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            putBoolean(ClipDescription.EXTRA_IS_SENSITIVE, isTextSensitive)
        } else {
            putBoolean("android.content.extra.IS_SENSITIVE", isTextSensitive)
        }
    }
    clipboard.setPrimaryClip(clip)
    // Only show a toast for Android 12 and lower.
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2)
        Toast.makeText(this, textForToast, Toast.LENGTH_SHORT).show()
}