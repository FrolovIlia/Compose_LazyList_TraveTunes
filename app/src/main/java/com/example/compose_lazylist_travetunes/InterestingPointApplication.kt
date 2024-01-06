package com.example.compose_lazylist_travetunes

import android.app.Application
import com.example.compose_lazylist_travetunes.persistence.InterestingPointDatabase

class InterestingPointApplication: Application() {
    val database: InterestingPointDatabase by lazy { InterestingPointDatabase.getDatabase(this) }

}
