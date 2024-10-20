package com.example.spacex.data.network.models

import com.google.gson.annotations.SerializedName

data class DocResponse(
    @SerializedName("flight_number") val flightNumber: Int = 0,
    @SerializedName("name") val missionName: String = ""
)