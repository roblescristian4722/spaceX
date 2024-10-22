package com.example.spacex

import com.example.spacex.data.network.def.SpaceXDefinitions
import com.example.spacex.data.network.impl.SpaceXImpl
import com.example.spacex.data.network.impl.onSuccess
import com.example.spacex.data.network.models.SpaceXResponse
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Test
import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIMEOUT: Long = 10
private const val RES_SIZE = 20

class ApiUnitTest {

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .build()

    private val gson = GsonConverterFactory.create(
        GsonBuilder().setLenient().create()
    )

    private val http = Retrofit.Builder()
        .baseUrl("https://api.spacexdata.com/")
        .client(httpClient)
        .addConverterFactory(gson)
        .build()
        .create(SpaceXDefinitions::class.java)

    private lateinit var queryHandler: SpaceXImpl

    @Test
    fun `Fetch data from SpaceX API`(): Unit = runBlocking {
        queryHandler = SpaceXImpl(http)
        queryHandler.getLaunches().onSuccess { res ->
            res as SpaceXResponse
            assertTrue(res.docs.size == RES_SIZE)
        }
    }
}