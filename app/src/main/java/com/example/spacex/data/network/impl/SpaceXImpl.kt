package com.example.spacex.data.network.impl

import com.example.spacex.data.network.def.SpaceXDefinitions

class SpaceXImpl(private val http: SpaceXDefinitions): BaseImpl() {

    suspend fun getLaunches(): QResult {
        return handleQuery(http.getLaunches())
    }
}