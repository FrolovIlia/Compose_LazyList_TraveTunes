package com.example.compose_lazylist_travetunes.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.compose_lazylist_travetunes.model.InterestingPoint

@Database(entities = [InterestingPoint::class], version = 1)
abstract class InterestingPointDatabase: RoomDatabase() {
    abstract fun InterestingPointDao(): InterestingPointDao
}