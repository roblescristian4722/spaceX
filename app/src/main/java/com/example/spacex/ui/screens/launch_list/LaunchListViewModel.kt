package com.example.spacex.ui.screens.launch_list

import androidx.lifecycle.ViewModel
import com.example.spacex.data.network.impl.SpaceXImpl
import com.example.spacex.data.network.impl.onFailure
import com.example.spacex.data.network.impl.onSuccess
import com.example.spacex.utils.Alert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LaunchListViewModel(private val httpClient: SpaceXImpl): ViewModel() {
    fun onSomething() {
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