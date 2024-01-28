package io.travel_tunes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.travel_tunes.model.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    /**
     * получить все города
     */
    @Query("SELECT * FROM cities ORDER BY name ASC")
    fun getAllCities(): Flow<List<CityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cityEntity: CityEntity)

    @Query("DELETE FROM cities")
    suspend fun deleteAll()

}