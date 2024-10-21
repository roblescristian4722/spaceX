package com.example.spacex.data.network.models

import com.google.gson.annotations.SerializedName

data class LinksResponse(
    @SerializedName("webcast") val webcast: String? = "",
    @SerializedName("article") val article: String? = "",
    @SerializedName("wikipedia") val wikipedia: String? = "",
    @SerializedName("patch") val patch: PatchResponse = PatchResponse()
)

data class PatchResponse(
    @SerializedName("small") val small: String? = "",
    @SerializedName("large") val large: String? = ""
)