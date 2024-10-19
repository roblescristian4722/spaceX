package com.example.spacex.ui.screens.launch_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacex.data.network.impl.SpaceXImpl
import com.example.spacex.data.network.impl.onFailure
import com.example.spacex.data.network.impl.onSuccess
import com.example.spacex.utils.Alert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LaunchListViewModel(private val httpClient: SpaceXImpl): ViewModel() {
    private val eventChannel = Channel<LaunchListScreenEvent>()
    val eventChannelFlow = eventChannel.receiveAsFlow()

    fun postEvent(event: LaunchListScreenEvent) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

    fun onSomething() {
        viewModelScope.launch {
            Alert("doing something")
            CoroutineScope(Dispatchers.IO).launch {
                httpClient.getLaunches()
                    .onSuccess {
                        Alert("Success!")
                    }
                    .onFailure {
                        Alert("Failure")
                    }
            }
        }
    }
}