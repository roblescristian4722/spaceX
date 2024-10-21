package com.example.spacex.ui.screens.launch_list_item

sealed class LaunchListItemScreenEvent {
    data class ItemClicked(val id: Int): LaunchListItemScreenEvent()
}