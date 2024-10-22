package com.example.spacex.ui.screens.launch_details

sealed class LaunchDetailsScreenEvent {
    data class FetchDetails(val id: Int): LaunchDetailsScreenEvent()
    data class YoutubeIntent(val id: String, val webcast: String): LaunchDetailsScreenEvent()
    data class UrlIntent(val url: String): LaunchDetailsScreenEvent()
}