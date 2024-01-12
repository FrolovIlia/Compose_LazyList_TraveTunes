package com.example.compose_lazylist_travetunes.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.compose_lazylist_travetunes.model.InterestingPoint

@Database(entities = [InterestingPoint::class], version = 1)
public abstract class InterestingPointDatabase: RoomDatabase() {

    abstract fun InterestingPointDao(): InterestingPointDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: InterestingPointDatabase? = null

        fun getDatabase(context: Context): InterestingPointDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {

                val nameDatabase = getDatabaseName()

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InterestingPointDatabase::class.java,
                    nameDatabase
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
        private fun getDatabaseName() = "interesting_points_db"
    }
}