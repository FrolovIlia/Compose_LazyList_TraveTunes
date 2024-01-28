package io.travel_tunes

import android.app.Application
import io.travel_tunes.data.InterestingPointDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class InterestingPointsApp: Application() {
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
}
