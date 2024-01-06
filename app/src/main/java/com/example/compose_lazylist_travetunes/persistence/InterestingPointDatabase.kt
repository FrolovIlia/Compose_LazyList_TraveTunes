package com.example.compose_lazylist_travetunes.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.compose_lazylist_travetunes.model.InterestingPoint

@Database(entities = [InterestingPoint::class], version = 1)
abstract class InterestingPointDatabase: RoomDatabase() {
    abstract fun InterestingPointDao(): InterestingPointDao

    companion object {
        @Volatile
        private var INSTANCE: InterestingPointDatabase? = null

        fun getDatabase(context: Context): InterestingPointDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InterestingPointDatabase::class.java,
                    "interesting_point_database"
                ).createFromAsset("interesting_point_database")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}