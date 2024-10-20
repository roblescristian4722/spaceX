package com.example.spacex.data.network.models

import com.google.gson.annotations.SerializedName

data class LaunchpadResponse(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("full_name") val fullName: String = "",
)