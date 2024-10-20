package com.example.spacex.data.network.models

const val ITEM_LIMIT = 20

data class OptionsModel(
    val limit: Int = ITEM_LIMIT
)