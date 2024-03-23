package io.travel_tunes.utils.coroutines

import android.util.Log
import android.view.View
import io.travel_tunes.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

private val JOB_KEY = R.id.view_coroutine_scope

private val errorCoroutineHandler = CoroutineExceptionHandler { _, throwable ->
    Log.e("error", "coroutines", throwable)
}

val View.viewScope: CoroutineScope
    get() {
        val storedScope = getTag(JOB_KEY) as? CoroutineScope
        if (storedScope != null) return storedScope

        val newScope = ViewCoroutineScope()
        if (isAttachedToWindow) {
            addOnAttachStateChangeListener(newScope)
            setTag(JOB_KEY, newScope)
        } else newScope.cancel()

        return newScope
    }

fun View.launchAtViewScope(block: suspend CoroutineScope.() -> Unit) {
    viewScope.launch(errorCoroutineHandler) {
        block.invoke(this)
    }
}

private class ViewCoroutineScope : CoroutineScope, View.OnAttachStateChangeListener {
    override val coroutineContext = SupervisorJob() + Dispatchers.Main

    override fun onViewAttachedToWindow(view: View) = Unit

    override fun onViewDetachedFromWindow(view: View) {
        coroutineContext.cancel()
        view.setTag(JOB_KEY, null)
    }
}