package com.example.spacex.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "launches")
data class LaunchesEntity(
    @PrimaryKey(true) val uid: Int = 0,
    @ColumnInfo(name = "flight_id") val flightId: Int,
    @ColumnInfo(name = "flight_number") val flightName: String,
    @ColumnInfo(name = "mission_name") val missionName: String,
    @ColumnInfo(name = "rocket_name") val rocketName: String,
    @ColumnInfo(name = "rocket_type") val rocketType: String,
    @ColumnInfo(name = "flight_details") val flightDetails: String
)