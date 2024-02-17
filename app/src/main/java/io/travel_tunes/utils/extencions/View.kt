package io.travel_tunes.utils.extencions

import android.view.View
import android.widget.TextView

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