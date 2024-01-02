package com.example.compose_lazylist_travetunes.persistence


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

import com.example.compose_lazylist_travetunes.model.InterestingPoint

@Dao
interface InterestingPointDao {

    @Query("SELECT * FROM Points")
    fun getAllInterestingPoints(): LiveData<List<InterestingPoint>>
}