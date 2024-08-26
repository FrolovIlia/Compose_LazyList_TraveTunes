package io.travel_tunes.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.travel_tunes.R

typealias OnSimpleClick = () -> Unit

data class ButtonDialogInfo(val text: String, val action: OnSimpleClick)

object DialogHelper {
    fun createAlertDialog(
        context: Context,
        title: String? = null,
        message: String? = null,
        positiveButton: ButtonDialogInfo? = null,
        negativeButton: ButtonDialogInfo? = null,
        isDialogCancelable: Boolean = false
    ): AlertDialog {
        return MaterialAlertDialogBuilder(context).setTitle(title).setMessage(message)
            .setPositiveButton(positiveButton?.text) { _, _ -> positiveButton?.action?.invoke() }
            .setNegativeButton(negativeButton?.text) { _, _ -> negativeButton?.action?.invoke() }
            .setBackgroundInsetStart(context.resources.getDimensionPixelOffset(R.dimen.spacing_16))
            .setBackgroundInsetEnd(context.resources.getDimensionPixelOffset(R.dimen.spacing_16))
            .setCancelable(isDialogCancelable)
            .show()
    }
}