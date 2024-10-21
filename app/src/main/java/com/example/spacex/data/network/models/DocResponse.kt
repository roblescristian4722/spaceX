package com.example.spacex.data.network.models

import com.google.gson.annotations.SerializedName

data class DocResponse(
    @SerializedName("id") val id: String = "",
    @SerializedName("flight_number") val flightNumber: Int = 0,
    @SerializedName("name") val launchName: String = "",
    @SerializedName("rocket") val rocket: RocketResponse = RocketResponse(),
    @SerializedName("links") val links: LinksResponse = LinksResponse(),
    @SerializedName("payloads") val payloads: List<PayloadsResponse> =
        listOf(),
    @SerializedName("launchpad") val launchpad: LaunchpadResponse =
        LaunchpadResponse(),
    @SerializedName("details") val details: String? = "",
    @SerializedName("success") val success: Boolean = false,
)