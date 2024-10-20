package com.example.spacex.data.network.models

import com.google.gson.annotations.SerializedName

data class SpaceXResponse(
    @SerializedName("docs") val docs: List<DocResponse> = listOf(),
    @SerializedName("totalDocs") val totalDocs: Int = 0,
    @SerializedName("offset") val offset: Int = 0,
    @SerializedName("limit") val limit: Int = 0,
    @SerializedName("page") val page: Int = 0
)
