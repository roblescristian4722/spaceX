package com.example.spacex.ui.screens.launch_list_item

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spacex.MainActivity
import com.example.spacex.di.dbModule
import com.example.spacex.di.httpModule
import com.example.spacex.di.viewModelModule
import com.example.spacex.ui.navigation.NavScreens
import com.example.spacex.ui.screens.launch_details.LaunchDetailsScreen
import com.example.spacex.ui.screens.launch_list.LaunchListScreen
import com.example.spacex.ui.theme.SpaceXTheme
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test

class LaunchListItemScreenKtTest {

    @get:Rule(order = 0)
    val koinTestRule = KoinTestRule(
        modules = listOf(dbModule, httpModule, viewModelModule)
    )

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeRule.setContent {
            val navController = rememberNavController()
            SpaceXTheme {
                NavHost(navController = navController, startDestination = NavScreens.ListScreen.route) {
                    composable(route = NavScreens.ListScreen.route) {
                        LaunchListScreen(navController)
                    }
                    composable(route = NavScreens.DetailsScreen.route) { entry ->
                        LaunchDetailsScreen(entry.arguments?.getString("id")?.toInt())
                    }
                }
            }
        }
    }

    @Test
    fun launchListItemScreen() {
    }

    @Test
    fun composableView() {
    }
}