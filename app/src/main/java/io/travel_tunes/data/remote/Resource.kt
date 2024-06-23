package io.travel_tunes.data.remote

// TODO: разобрать разные кейсы ошибок, чтобы выаодить информацию правильно юзеру (например, включите инет)
sealed class Resource<out T> {

    data class Success<out T>(val value: T) : Resource<T>()

    data class Failure(
        private val isNetworkError: Boolean = false,
        private val errorCode: Int? = null,
        private val errorBodyString: String? = null,
        private val throwable: Throwable? = null
    ) : Resource<Nothing>() {
        fun isNetworkError() = isNetworkError
        fun getErrorCode() = errorCode
        fun isErrorCode401() = errorCode == 401
        fun isErrorCode403() = errorCode == 403
        fun getErrorBodyAsString() = errorBodyString
        fun getThrowable() = throwable
    }

    data object Loading : Resource<Nothing>()
}

fun Resource<*>.isFailureErrorAuth(): Boolean {
    return this is Resource.Failure && this.isErrorCode401()
}
fun Resource<*>.isFailureErrorForbidden(): Boolean {
    return this is Resource.Failure && this.isErrorCode403()
}
fun Resource<*>.isFailure() = this is Resource.Failure
fun Resource<*>.isSuccess() = this is Resource.Success