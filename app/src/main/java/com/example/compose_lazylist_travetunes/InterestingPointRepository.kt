package com.example.compose_lazylist_travetunes

import com.example.compose_lazylist_travetunes.model.CityEntity
import com.example.compose_lazylist_travetunes.model.InterestingPointEntity
import com.example.compose_lazylist_travetunes.persistence.CityDao
import com.example.compose_lazylist_travetunes.persistence.InterestingPointDao
import kotlinx.coroutines.flow.Flow

class InterestingPointRepository(
    private val interestingPointDao: InterestingPointDao,
    private val cityDao: CityDao
) {

    val allPoints: Flow<List<InterestingPointEntity>> =
        interestingPointDao.getAllInterestingPoints()
    val allCities: Flow<List<CityEntity>> = cityDao.getAllCities()
}