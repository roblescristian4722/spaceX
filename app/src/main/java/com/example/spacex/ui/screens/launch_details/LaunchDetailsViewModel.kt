package com.example.spacex.ui.screens.launch_details

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.spacex.data.network.impl.SpaceXImpl
import com.example.spacex.data.network.impl.onFailure
import com.example.spacex.data.network.impl.onSuccess
import com.example.spacex.utils.Alert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LaunchDetailsViewModel(private val httpClient: SpaceXImpl): ViewModel() {
}