package com.example.compose_lazylist_travetunes.persistence


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.example.compose_lazylist_travetunes.model.InterestingPoint


@Dao
interface InterestingPointDao {

    @Query("SELECT * FROM Points ORDER BY title ASC")
    fun getAllInterestingPoints(): LiveData<List<InterestingPoint>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(interestingPoint: InterestingPoint)

}