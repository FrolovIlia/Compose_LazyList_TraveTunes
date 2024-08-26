package io.travel_tunes.utils.extencions

import androidx.lifecycle.Lifecycle

fun Lifecycle.isActiveState(): Boolean = this.currentState in listOf(
    Lifecycle.State.RESUMED,
    Lifecycle.State.STARTED
)