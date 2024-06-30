package io.travel_tunes.utils.extencions

import android.view.View
import android.widget.TextView
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.findViewTreeLifecycleOwner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun View.changeVisibilityInvisible() {
    if (this.visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
}

fun View.changeVisibility(isVisible: Boolean) {
    val state = if (isVisible) View.VISIBLE else View.GONE
    if (this.visibility != state) {
        visibility = state
    }
}

fun View.changeEnabled(isEnabled: Boolean) {
    if (isEnabled == this.isEnabled) {
        return
    } else {
        this.isEnabled = isEnabled
    }
}

fun TextView.changeText(newText: String?) {
    if (text != newText) {
        text = newText
    }
}

fun TextView.showTextOrGoneView(text: String?) {
    if (text.isNullOrBlank()) {
        this.changeVisibility(false)
    } else {
        this.text = text
        changeVisibility(true)
    }
}

fun View.delayOnLifecycle(
    durationInMillis: Long, dispatcher: CoroutineDispatcher = Dispatchers.Main, block: () -> Unit
): Job? = findViewTreeLifecycleOwner()?.let { lifecycleOwner ->
    lifecycleOwner.lifecycle.coroutineScope.launch(dispatcher) {
        delay(durationInMillis)
        block()
    }
}