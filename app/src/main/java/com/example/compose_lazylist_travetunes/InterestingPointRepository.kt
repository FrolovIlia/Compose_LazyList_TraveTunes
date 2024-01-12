package com.example.compose_lazylist_travetunes

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.compose_lazylist_travetunes.model.InterestingPoint
import com.example.compose_lazylist_travetunes.persistence.InterestingPointDao


class InterestingPointRepository(private val interestingPointDao: InterestingPointDao) {

    val allWords: LiveData<List<InterestingPoint>> = interestingPointDao.getAllInterestingPoints()

    @Suppress
    @WorkerThread
    suspend fun insert(interestingPoint: InterestingPoint) {
        interestingPointDao.insert(interestingPoint)
    }
}