package com.example.spacex.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LaunchesEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun launchesDao(): LaunchesDao
}