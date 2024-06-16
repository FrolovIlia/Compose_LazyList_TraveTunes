package io.travel_tunes.di.commonModules

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.travel_tunes.data.remote.PaymentsApi
import io.travel_tunes.di.ChuckInterceptor
import io.travel_tunes.di.TokenInterceptor
import io.travel_tunes.utils.BuildConfigUtils
import io.travel_tunes.data.local.prefs.PreferenceManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    @TokenInterceptor
    fun provideTokenInterceptor(
        preferences: PreferenceManager
    ): Interceptor {
        return Interceptor { chain ->
            val authToken = runBlocking { preferences.authToken.first() }
            chain.proceed(chain.request().newBuilder().also {
                if (!authToken.isNullOrBlank())
                    it.addHeader("Authorization", "Bearer $authToken")
                it.addHeader("Accept-Language", Locale.getDefault().language)
            }.build())
        }
    }

    @Provides
    @Singleton
    @ChuckInterceptor
    fun provideChuckerInterceptor(
        context: Context
    ): Interceptor {
        // Create the Collector
        val chuckerCollector = ChuckerCollector(
            context = context,
            // Toggles visibility of the push notification
            showNotification = true,
            // Allows to customize the retention period of collected data
            retentionPeriod = RetentionManager.Period.ONE_WEEK
        )

        // Create the Interceptor
        return ChuckerInterceptor.Builder(context)
            // The previously created Collector
            .collector(chuckerCollector)
            // The max body content length in bytes, after this responses will be truncated.
            .maxContentLength(250_000L)
            // List of headers to replace with ** in the Chucker UI
            .redactHeaders("Auth-Token", "Bearer")
            // Read the whole response body even when the client does not consume the response completely.
            // This is useful in case of parsing errors or when the response body
            // is closed before being read like in Retrofit with Void and Unit types.
            .alwaysReadResponseBody(true)
            // Use decoder when processing request and response bodies. When multiple decoders are installed they
            // are applied in an order they were added.
//            .addBodyDecoder(decoder)
            // Controls Android shortcut creation. Available in SNAPSHOTS versions only at the moment
//            .createShortcut(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
//            .excludeFieldsWithoutExposeAnnotation()
            .setDateFormat(DateFormat.LONG)
            .create()
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ChuckInterceptor chuckInterceptor: Interceptor,
        @TokenInterceptor tokenInterceptor: Interceptor
    ): OkHttpClient {
        with(OkHttpClient.Builder()) {
            addInterceptor(tokenInterceptor)
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            if (BuildConfigUtils.isDebugMode()) {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                addInterceptor(chuckInterceptor)
            }
            return build()
        }

    }

    @Provides
    @Singleton
    fun provideRetrofitPayments(
        client: OkHttpClient,
        gson: Gson
    ): PaymentsApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfigUtils.getBaseUrl())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(PaymentsApi::class.java)
    }
}