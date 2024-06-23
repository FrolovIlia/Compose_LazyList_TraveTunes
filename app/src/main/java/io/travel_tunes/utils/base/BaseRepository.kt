package io.travel_tunes.utils.base

import android.content.Context
import com.google.gson.JsonSyntaxException
import io.travel_tunes.data.remote.Resource
import io.travel_tunes.utils.CrashlyticsUtils
import io.travel_tunes.utils.hasInternetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository(private val appContext: Context) {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            if (!hasInternetConnection(context = appContext)) {
                Resource.Failure(isNetworkError = true)
            } else {
                try {
                    Resource.Success(apiCall.invoke())
                } catch (throwable: Throwable) {
                    CrashlyticsUtils.sendThrowableNonFatal(throwable)
                    when (throwable) {
                        is HttpException -> {
                            Resource.Failure(
                                errorCode = throwable.code(),
                                errorBodyString = throwable.response()?.errorBody()?.string()
                            )
                        }

                        is JsonSyntaxException -> {
                            Resource.Failure(throwable = throwable)
                        }

                        else -> {
                            Resource.Failure(isNetworkError = true)
                        }
                    }
                }
            }
        }
    }
}