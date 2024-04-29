package io.travel_tunes.utils.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.travel_tunes.utils.CrashlyticsUtils
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

open class BaseViewModel: ViewModel() {
    private val errorCoroutineHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
        CrashlyticsUtils.sendThrowableNonFatal(throwable)
    }

    fun launchAtViewModelScope(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(errorCoroutineHandler) {
            block.invoke(this)
        }
    }
}