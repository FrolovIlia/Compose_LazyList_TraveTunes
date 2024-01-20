package com.example.compose_lazylist_travetunes.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.compose_lazylist_travetunes.model.InterestingPointEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InterestingPointDao {

    @Query("SELECT * FROM points ORDER BY title ASC")
    fun getAllInterestingPoints(): Flow<List<InterestingPointEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(interestingPointEntity: InterestingPointEntity)

    @Query("DELETE FROM points")
    suspend fun deleteAll()

}