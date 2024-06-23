package io.travel_tunes.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.travel_tunes.R

object DialogUtils {

    @SuppressLint("ResourceType")
    @JvmOverloads
    fun createProgressDialog(
        context: Context,
        text: String? = "",
        dialog: AlertDialog? = null
    ): AlertDialog {
        return if (dialog != null)
            updateProgressDialog(context, text, dialog)
        else
            createProgressDialogBuilder(context, text).show()
    }

    private fun updateProgressDialog(
        context: Context,
        text: String? = "",
        dialog: AlertDialog
    ): AlertDialog {
        val message = when {
            !text.isNullOrEmpty() -> text
            else -> context.resources.getString(R.string.message_loading)
        }
        val textView = dialog.findViewById<TextView>(R.id.progress_text)
        textView?.text = message
        return dialog
    }

    private fun createProgressDialogBuilder(
        context: Context,
        text: String? = ""
    ): MaterialAlertDialogBuilder {
        val message = when {
            !text.isNullOrEmpty() -> text
            else -> context.resources.getString(R.string.message_loading)
        }

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_loading, null, false)
        val textView = view.findViewById<TextView>(R.id.progress_text)
        textView.text = message

        return MaterialAlertDialogBuilder(context)
            .setBackgroundInsetStart(context.resources.getDimensionPixelOffset(R.dimen.spacing_16))
            .setBackgroundInsetEnd(context.resources.getDimensionPixelOffset(R.dimen.spacing_16))
            .setView(view)
            .setCancelable(false)
    }

    fun safetyDismissDialog(dialog: AlertDialog?) {
        if (dialog != null && dialog.isShowing) {
            dialog.dismiss()
        }
    }
}