package com.example.spacex.data.network.responses

import com.google.gson.annotations.SerializedName

data class DocResponse(
    @SerializedName("flight_number") val flightNumber: Int = 0,
    @SerializedName("name") val missionName: String = ""
)