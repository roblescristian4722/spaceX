package com.example.spacex.ui.screens.launch_list_item

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import com.example.spacex.data.db.LaunchesEntity
import com.example.spacex.ui.navigation.NavScreens
import com.example.spacex.utils.TestTags
import com.example.spacex.ui.screens.launch_details.ComposableView as LaunchDetailsScreen
import com.example.spacex.ui.screens.launch_list.ComposableView as LaunchListScreen
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule

import org.junit.Test

class LaunchListItemScreenTest {

    @get:Rule
    val rule = createComposeRule()
    private val entities = listOf(
        LaunchesEntity(flightId = 1),
        LaunchesEntity(flightId = 2),
        LaunchesEntity(flightId = 3),
        LaunchesEntity(flightId = 4),
        LaunchesEntity(flightId = 5),
    )
    val dataRows = listOf(
        "Mission" to "",
        "Rocket name" to "",
        "Rocket type" to "",
        "Launch site" to "",
        "Flight details" to ""
    )
    private val loading = false
    private val prLoading = false
    private lateinit var navController: TestNavHostController

    @OptIn(ExperimentalMaterial3Api::class)
    @Before
    fun setUp() {
        rule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NavHost(navController = navController, startDestination = NavScreens.ListScreen.route) {
                composable(route = NavScreens.ListScreen.route) {
                    LaunchListScreen(
                        navController = navController,
                        items = entities,
                        loading = loading,
                        pullRefreshState = rememberPullToRefreshState(),
                        pullRefreshLoading = prLoading,
                        onRefresh = {}
                    )
                }
                composable(route = NavScreens.DetailsScreen.route) { entry ->
                    LaunchDetailsScreen(
                        id = (entry.arguments?.getString("id")?.toInt()) ?: 0,
                        details = entities.first(),
                        {},
                        {}
                    )
                }
            }
        }
    }


    @Test
    fun listItemsDisplayed(): Unit = runBlocking {
        rule.onAllNodesWithTag(TestTags.LAUNCH_LIST_ITEM_SCREEN).apply {
            Assert.assertEquals(entities.size, fetchSemanticsNodes().size)
        }
    }

    @Test
    fun verifyStartDestination() {
        rule.onAllNodesWithTag(TestTags.LAUNCH_LIST_ITEM_SCREEN).apply {
            get(0).performClick()
        }
        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(NavScreens.DetailsScreen.route, route)
    }

    @Test
    fun listDataRows() {
        rule.onAllNodesWithTag(TestTags.LAUNCH_LIST_ITEM_SCREEN).apply {
            get(0).performClick()
        }
        rule.onAllNodesWithTag(TestTags.DATA_ROW_ITEM_SCREEN).apply{
            Assert.assertEquals(dataRows.size, fetchSemanticsNodes().size - 1)
        }
    }
}