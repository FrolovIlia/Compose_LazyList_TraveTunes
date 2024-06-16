package io.travel_tunes

import android.app.Application
import android.content.Context
import io.travel_tunes.data.InterestingPointDatabase
import io.travel_tunes.di.AppComponent
import io.travel_tunes.di.DaggerAppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber.*
import timber.log.Timber.Forest.plant


class InterestingPointsApp: Application() {
    lateinit var appComponent: AppComponent

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { InterestingPointDatabase.getDatabase(this, applicationScope) }
    val repository by lazy {
        InterestingPointRepository(
            database.interestingPointDao(),
            database.cityDao()
        )
    }

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