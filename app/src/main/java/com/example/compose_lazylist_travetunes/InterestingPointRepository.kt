package com.example.compose_lazylist_travetunes

import androidx.annotation.WorkerThread
import com.example.compose_lazylist_travetunes.model.InterestingPointEntity
import com.example.compose_lazylist_travetunes.persistence.InterestingPointDao
import kotlinx.coroutines.flow.Flow

class InterestingPointRepository(private val interestingPointDao: InterestingPointDao) {

    val allPoints: Flow<List<InterestingPointEntity>> = interestingPointDao.getAllInterestingPoints()

    @Suppress
    @WorkerThread
    suspend fun insert(interestingPointEntity: InterestingPointEntity) {
        interestingPointDao.insert(interestingPointEntity)
    }
}