package com.example.spacex.data.network.models

import com.google.gson.annotations.SerializedName

data class PayloadsResponse(
    @SerializedName("name") val name: String = "",
    @SerializedName("type") val type: String = "",
    @SerializedName("id") val id: String = "",
)