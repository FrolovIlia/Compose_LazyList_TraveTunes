package io.travel_tunes.utils.base

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import io.travel_tunes.utils.DialogUtils.createProgressDialog
import io.travel_tunes.utils.DialogUtils.safetyDismissDialog

open class BaseActivity : AppCompatActivity() {
    private var mProgress: AlertDialog? = null

    open fun hideProgressDialog() {
        if (mProgress != null) {
            safetyDismissDialog(mProgress)
            mProgress = null
        }
    }

    open fun showProgressDialog(message: String?) {
        mProgress = createProgressDialog(this, message, mProgress)
    }
}