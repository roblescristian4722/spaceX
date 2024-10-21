package com.example.spacex.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spacex.ui.screens.launch_details.LaunchDetailsScreen
import com.example.spacex.ui.screens.launch_list.LaunchListScreen

@Preview
@Composable
fun NavEntry() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavScreens.ListScreen.route) {
        composable(route = NavScreens.ListScreen.route) {
            LaunchListScreen(navController)
        }
        composable(route = NavScreens.DetailsScreen.route) { entry ->
            LaunchDetailsScreen(navController, entry.arguments?.getString("id")?.toInt())
        }
    }
}