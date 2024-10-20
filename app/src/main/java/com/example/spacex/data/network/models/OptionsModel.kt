package com.example.spacex.data.network.models

const val ITEM_LIMIT = 20

data class OptionsModel(
    val limit: Int = ITEM_LIMIT,
    val populate: List<PopulateModel> = listOf()
)

data class PopulateModel(
    val path: String = "",
)