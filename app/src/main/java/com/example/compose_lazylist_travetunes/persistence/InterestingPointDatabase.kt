package com.example.compose_lazylist_travetunes.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.compose_lazylist_travetunes.Constants
import com.example.compose_lazylist_travetunes.data.DataGenerator
import com.example.compose_lazylist_travetunes.model.CityEntity
import com.example.compose_lazylist_travetunes.model.InterestingPointEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [InterestingPointEntity::class, CityEntity::class], version = 2)
abstract class InterestingPointDatabase: RoomDatabase() {

    abstract fun interestingPointDao(): InterestingPointDao
    abstract fun cityDao(): CityDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: InterestingPointDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): InterestingPointDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {

                val nameDatabase = getDatabaseName()

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InterestingPointDatabase::class.java,
                    nameDatabase
                )
//                    FIXME
                    .addCallback(InterestingPointsDatabaseCallback(scope))
//                    .createFromAsset("interesting_point_database.db")

                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
        private fun getDatabaseName() = Constants.DATABASE_NAME
    }

    private class InterestingPointsDatabaseCallback(
        private val scope: CoroutineScope
    ): Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val interestingPointDao = database.interestingPointDao()
                    val cityDao = database.cityDao()

                    // Delete all content here.
                    interestingPointDao.deleteAll()

                    // Add sample words.
                    val listOfPoints = DataGenerator.getDefaultPointsList()
                    listOfPoints.forEach { point ->
                        interestingPointDao.insert(point)
                    }


                    // Delete all content here.
                    cityDao.deleteAll()

                    // Add sample words.
                    val listOfCities = DataGenerator.getDefaultCities()
                    listOfCities.forEach { point ->
                        cityDao.insert(point)
                    }
                }
            }
        }
    }
}