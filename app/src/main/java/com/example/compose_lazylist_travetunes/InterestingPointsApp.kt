package com.example.compose_lazylist_travetunes

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.compose_lazylist_travetunes.persistence.InterestingPointDatabase
import android.content.Intent
import android.net.Uri
import com.example.compose_lazylist_travetunes.persistence.InterestingPointDao


class InterestingPointsApp: Application() {
    var db: InterestingPointDatabase? = null

    init {
        INSTANCE = this
    }


    private fun getDb(): InterestingPointDatabase {
        return if (db != null){
            db!!
        } else {
            db = Room.databaseBuilder(
                INSTANCE!!.applicationContext,
                InterestingPointDatabase::class.java, Constants.DATABASE_NAME
            ).fallbackToDestructiveMigration()// remove in prod
                .build()
            db!!
        }
    }

    companion object {
        private var INSTANCE: InterestingPointsApp? = null

        fun getDao(): InterestingPointDao {
            return INSTANCE!!.getDb().InterestingPointDao()
        }

//        fun getUriPermission(uri: Uri){
//            INSTANCE!!.applicationContext.contentResolver.takePersistableUriPermission(
//                uri,
//                Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
//            )
//        }
//

}





}
