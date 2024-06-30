package io.travel_tunes.data.remote

import io.travel_tunes.model.remote.FailureDetails

sealed class Resource<out T> {

    data class Success<out T>(val value: T) : Resource<T>()

    data class Failure(
        private val isNetworkError: Boolean = false,
        private val failureDetails: FailureDetails? = null,
        private val throwable: Throwable? = null
    ) : Resource<Nothing>() {
        fun isNetworkError() = isNetworkError
        fun getFailureDetails() = failureDetails
        fun getThrowable() = throwable
    }
}
fun Resource<*>.isSuccess() = this is Resource.Success