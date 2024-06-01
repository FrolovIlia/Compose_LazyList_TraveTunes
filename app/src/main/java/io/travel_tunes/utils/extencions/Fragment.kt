package io.travel_tunes.utils.extencions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private fun Fragment.launchWhenAtLeastLifecycleState(
    state: Lifecycle.State = Lifecycle.State.RESUMED,
    block: suspend CoroutineScope.() -> Unit
) {
    lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(state, block)
    }
}

fun Fragment.launchWhenAtLeastLifecycleStateStarted(block: suspend CoroutineScope.() -> Unit) {
    launchWhenAtLeastLifecycleState(state = Lifecycle.State.STARTED, block)
}