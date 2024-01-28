package io.travel_tunes

import io.travel_tunes.model.CityEntity
import io.travel_tunes.model.InterestingPointEntity
import io.travel_tunes.data.CityDao
import io.travel_tunes.data.InterestingPointDao
import kotlinx.coroutines.flow.Flow

class InterestingPointRepository(
    private val interestingPointDao: InterestingPointDao,
    private val cityDao: CityDao
) {

    val allPoints: Flow<List<InterestingPointEntity>> =
        interestingPointDao.getAllInterestingPoints()
    val allCities: Flow<List<CityEntity>> = cityDao.getAllCities()
}