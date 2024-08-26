package io.travel_tunes.utils.extencions

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import io.travel_tunes.R
import io.travel_tunes.utils.CrashlyticsUtils

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

fun Activity.openEmailApp(emailAddress: String?) {
    if (emailAddress.isNullOrBlank()) return
    val context = this
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data =
        Uri.parse(context.getString(R.string.contacts_mail_link_pattern, emailAddress))
    try {
        context.startActivity(
            Intent.createChooser(
                intent,
                context.getString(R.string.contacts_select_mail)
            )
        )
    } catch (th: Throwable) {
        CrashlyticsUtils.sendThrowableNonFatal(th)
        Toast.makeText(context, R.string.application_not_found, Toast.LENGTH_SHORT).show()
    }
}

fun Activity.isLocationPermissionFineGranted(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}