package com.example.spacex.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LaunchesDao {
    @Query("SELECT * FROM launches")
    fun getAll(): List<LaunchesEntity>

    @Query("SELECT * FROM launches WHERE flight_id = :flightIds")
    fun getByFlightIds(flightIds: IntArray): List<LaunchesEntity>

    @Query("SELECT * FROM launches WHERE uid = :ids")
    fun getByIds(ids: IntArray): List<LaunchesEntity>

    @Insert
    fun insertAll(vararg launches: LaunchesEntity)

    @Query("DELETE FROM launches")
    fun deleteAll()
}