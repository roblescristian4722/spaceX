package com.example.spacex.ui.screens.launch_list

import androidx.lifecycle.viewModelScope
import com.example.spacex.data.db.LaunchesDao
import com.example.spacex.data.db.LaunchesEntity
import com.example.spacex.data.network.impl.SpaceXImpl
import com.example.spacex.data.network.impl.onFailure
import com.example.spacex.data.network.impl.onSuccess
import com.example.spacex.data.network.models.SpaceXResponse
import com.example.spacex.ui.screens.commons.BaseViewModel
import com.example.spacex.utils.Alert
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class LaunchListViewModel(
    private val httpClient: SpaceXImpl,
    private val db: LaunchesDao):
BaseViewModel<LaunchListScreenEvent>() {

    private val _launches = MutableStateFlow<List<LaunchesEntity>>(listOf())
    val launches = _launches.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _pullRefreshLoading = MutableStateFlow(false)
    val pullRefreshLoading = _pullRefreshLoading.asStateFlow()

    fun enableLoadingScreen() {
        _loading.value = true
    }

    fun enablePullRefreshLoading() {
        _pullRefreshLoading.value = true
    }

    fun disableLoadingScreen() {
        _loading.value = false
        _pullRefreshLoading.value = false
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        viewModelScope.launch {
            when (throwable) {
                is UnknownHostException -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        _launches.value = db.getAll()
                        postEvent(LaunchListScreenEvent.FinishedFetchingData)
                    }
                }
            }
            throwable.printStackTrace()
        }
    }

    fun fetchData() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                httpClient.getLaunches()
                    .onSuccess { res ->
                        res as SpaceXResponse
                        db.deleteAll()
                        res.docs.forEach { doc ->
                            val launch = LaunchesEntity(
                                missionName = doc.launchName,
                                flightId = doc.flightNumber,
                                rocketName = doc.rocket.name,
                                rocketType = doc.payloads.first().type,
                                flightDetails = doc.details,
                                launchSite = doc.launchpad.name,
                                smallPatch = doc.links.patch.small,
                                largePatch = doc.links.patch.large,
                                ytWebcast = doc.links.webcast,
                                ytId = doc.links.youtubeId,
                                wikipedia = doc.links.wikipedia,
                                article = doc.links.article)
                            db.insertAll(launch)
                        }
                        _launches.value = db.getAll()
                        postEvent(LaunchListScreenEvent.FinishedFetchingData)
                    }
                    .onFailure {
                        Alert("Couldn't fetch data")
                    }
            }
        }
    }
}