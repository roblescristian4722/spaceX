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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LaunchListViewModel(
    private val httpClient: SpaceXImpl,
    private val db: LaunchesDao):
BaseViewModel<LaunchListScreenEvent>() {

    fun onSomething() {
        viewModelScope.launch {
            Alert("doing something")
            CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                httpClient.getLaunches()
                    .onSuccess { res ->
                        res as SpaceXResponse
                        db.deleteAll()
                        db.insertAll(LaunchesEntity(
                            flightName = "asdfñlkj",
                            flightId = 1,
                            missionName = "asdñflkj",
                            rocketName = "asdñflkj",
                            rocketType = "asdfñklj",
                            flightDetails = "asdñflkj"))
                        val launch = db.getByFlightIds(intArrayOf(1))
                        Alert("launch: $launch")
                    }
                    .onFailure {
                        Alert("Failure")
                    }
            }
        }
    }
}