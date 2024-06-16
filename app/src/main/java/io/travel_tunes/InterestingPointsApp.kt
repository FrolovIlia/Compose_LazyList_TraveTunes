package io.travel_tunes

import android.app.Application
import android.content.Context
import io.travel_tunes.di.AppComponent
import io.travel_tunes.di.DaggerAppComponent
import timber.log.Timber.DebugTree
import timber.log.Timber.Forest.plant


class InterestingPointsApp: Application() {
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
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is InterestingPointsApp -> this.appComponent
        else -> this.applicationContext.appComponent
    }