package io.travel_tunes

import android.app.Application
import android.content.Context
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapsSdkInitializedCallback
import io.travel_tunes.di.AppComponent
import io.travel_tunes.di.DaggerAppComponent
import timber.log.Timber
import timber.log.Timber.DebugTree
import timber.log.Timber.Forest.plant


class InterestingPointsApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .context(this)
            .build()

        if (BuildConfig.DEBUG) {
            plant(DebugTree())
        }
        initMapSdk()
    }

    private fun initMapSdk() {
        val callback = OnMapsSdkInitializedCallback { renderer ->
            when (renderer) {
                MapsInitializer.Renderer.LATEST -> Timber.e(
                    "MapsInit = The latest version of the renderer is used."
                )

                MapsInitializer.Renderer.LEGACY -> Timber.e(
                    "MapsInit = The legacy version of the renderer is used."
                )
            }
        }
        MapsInitializer.initialize(this, MapsInitializer.Renderer.LEGACY, callback)
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is InterestingPointsApp -> this.appComponent
        else -> this.applicationContext.appComponent
    }

