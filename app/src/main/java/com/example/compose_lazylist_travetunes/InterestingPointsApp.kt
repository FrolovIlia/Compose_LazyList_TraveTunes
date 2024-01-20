package com.example.compose_lazylist_travetunes

import android.app.Application
import androidx.room.Room
import com.example.compose_lazylist_travetunes.persistence.InterestingPointDatabase
import com.example.compose_lazylist_travetunes.persistence.InterestingPointDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class InterestingPointsApp: Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { InterestingPointDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { InterestingPointRepository(database.interestingPointDao(), database.cityDao()) }
}
