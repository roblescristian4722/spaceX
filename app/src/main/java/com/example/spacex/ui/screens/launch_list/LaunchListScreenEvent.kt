package com.example.spacex.ui.screens.launch_list

sealed class LaunchListScreenEvent {
    data object Print: LaunchListScreenEvent()
}