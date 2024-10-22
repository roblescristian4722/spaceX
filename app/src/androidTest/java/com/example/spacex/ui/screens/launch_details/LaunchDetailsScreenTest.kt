package com.example.spacex.ui.screens.launch_details

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.performClick
import com.example.spacex.data.db.LaunchesEntity
import com.example.spacex.utils.TestTags
import org.junit.Assert
import com.example.spacex.ui.screens.launch_details.ComposableView as LaunchDetailsScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LaunchDetailsScreenTest {

    @get:Rule
    val rule = createComposeRule()

    private val expectedDataRows = 5
    private val entity = LaunchesEntity(
        flightId = 1,
        missionName = "mission name",
        rocketType = "rocket type",
        rocketName = "rocket name",
        launchSite = "launch site",
        flightDetails = "flight details"
    )

    @Before
    fun setUp() {
        rule.setContent {
            LaunchDetailsScreen(
                id = 1,
                details = entity,
                ytCall = {},
                urlCall = {}
            )
        }
    }

    @Test
    fun listDataRows() {
        rule.onAllNodesWithTag(TestTags.DATA_ROW_ITEM_SCREEN).filter(
            SemanticsMatcher("isPlaced") { it.layoutInfo.isPlaced }
        ).apply {
            Assert.assertEquals(expectedDataRows, fetchSemanticsNodes().size - 1)
        }
    }
}