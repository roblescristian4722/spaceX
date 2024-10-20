package com.example.spacex.data.network.def

import com.example.spacex.data.network.models.QueryModel
import com.example.spacex.data.network.models.SpaceXResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SpaceXDefinitions {
    @Headers("Accept: application/json")
    @POST("/v4/launches/query")
    suspend fun getLaunches(@Body query: QueryModel): retrofit2
        .Response<SpaceXResponse>
}