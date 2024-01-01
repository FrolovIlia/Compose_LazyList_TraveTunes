package com.example.compose_lazylist_travetunes.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

import com.example.compose_lazylist_travetunes.model.InterestingPoint

@Dao
interface InterestingPointDao {

    @Query("SELECT * FROM InterestingPoint ORDER BY titleID DESC")
    fun getNotes() : LiveData<List<InterestingPoint>>
}