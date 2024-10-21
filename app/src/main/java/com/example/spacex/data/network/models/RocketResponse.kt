package com.example.spacex.data.network.models

import com.google.gson.annotations.SerializedName

data class RocketResponse(
    @SerializedName("name") val name: String = "",
    @SerializedName("type") val type: String = "",
    @SerializedName("active") val active: Boolean = false,
)