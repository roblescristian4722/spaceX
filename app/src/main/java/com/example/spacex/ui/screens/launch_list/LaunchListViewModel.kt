package com.example.spacex.ui.screens.launch_list

import android.content.Context
import androidx.lifecycle.viewModelScope
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
    private val httpClient: SpaceXImpl):
BaseViewModel<LaunchListScreenEvent>() {

    fun onSomething() {
        viewModelScope.launch {
            Alert("doing something")
            CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                httpClient.getLaunches()
                    .onSuccess { res ->
                        res as SpaceXResponse
                    }
                    .onFailure {
                        Alert("Failure")
                    }
            }
        }
    }
}