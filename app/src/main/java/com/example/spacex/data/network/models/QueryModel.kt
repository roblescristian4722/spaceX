package com.example.spacex.data.network.models

data class QueryModel(
    val query: Any = Object(),
    val options: OptionsModel = OptionsModel()
)