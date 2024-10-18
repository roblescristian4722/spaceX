package com.example.spacex.ui.navigation

sealed class NavScreens(val route: String) {
    data object ListScreen: NavScreens("list")
    data object DetailsScreen: NavScreens("details")
}