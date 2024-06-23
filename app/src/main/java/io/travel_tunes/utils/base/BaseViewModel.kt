package io.travel_tunes.utils.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.travel_tunes.R
import io.travel_tunes.utils.CrashlyticsUtils
import io.travel_tunes.utils.MessageProgress
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

open class BaseViewModel: ViewModel() {
    private val errorCoroutineHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
        CrashlyticsUtils.sendThrowableNonFatal(throwable)
    }

    private val _progressDialogText = MutableLiveData<MessageProgress?>()
    val progressDialogText
        get() = _progressDialogText

    fun launchAtViewModelScope(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(errorCoroutineHandler) {
            block.invoke(this)
        }
    }

    fun showProgressDialog(
        textMessage: String? = null
    ) {
        val resStringMessage = R.string.message_loading
        _progressDialogText.postValue(MessageProgress(textMessage, resStringMessage))
    }

    fun clearProgressDialog() {
        _progressDialogText.postValue(null)
    }
}