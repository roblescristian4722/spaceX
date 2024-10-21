package com.example.spacex.ui.screens.launch_details

sealed class LaunchDetailsScreenEvent {
    data class FetchDetails(val id: Int): LaunchDetailsScreenEvent()
}