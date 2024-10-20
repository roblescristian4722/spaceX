package com.example.spacex.ui.screens.commons

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacex.utils.Alert
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

/**
 * [BaseViewModel] Base class for ViewModels whose inherited classes need to
 * declare a sealed class [T] for ScreenEvents
 */
open class BaseViewModel<T>: ViewModel() {
    private val eventChannel = Channel<T>()
    /**
     * [eventChannelFlow] this [Flow] is used by [ObserveAsEvents] for
     * ScreenEvent [T] detection
     */
    val eventChannelFlow = eventChannel.receiveAsFlow()

    /**
     * [postEvent] calls an ScreenEvent [T]
     */
    fun postEvent(event: T) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        viewModelScope.launch {
            when (throwable) {
                is UnknownHostException -> {
                    Alert("No internet")
                }
            }
            throwable.printStackTrace()
        }
    }

}