package com.example.estationary.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [eStationary::class], version = 1, exportSchema = false)
abstract class eStationaryDB : RoomDatabase() {

    abstract val estationaryDAO: eStationaryDAO

    companion object {
        @Volatile
        private var INSTANCE: eStationaryDB? = null

        fun getInstance(context: Context) : eStationaryDB {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        eStationaryDB::class.java,
                        "estationary_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}