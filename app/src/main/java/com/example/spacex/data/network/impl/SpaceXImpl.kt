package com.example.spacex.data.network.impl

import com.example.spacex.data.network.def.SpaceXDefinitions
import com.example.spacex.data.network.models.OptionsModel
import com.example.spacex.data.network.models.PopulateModel
import com.example.spacex.data.network.models.QueryModel

class SpaceXImpl(private val http: SpaceXDefinitions): BaseImpl() {

    suspend fun getLaunches(): QResult {
        return handleQuery(
            http.getLaunches(
                QueryModel(options = OptionsModel(populate = listOf(
                PopulateModel("payloads"),
                PopulateModel("launchpad"),
                PopulateModel("rocket")
            )))))
    }
}