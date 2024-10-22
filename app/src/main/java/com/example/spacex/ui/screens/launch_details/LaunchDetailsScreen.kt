package com.example.spacex.ui.screens.launch_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.spacex.R
import com.example.spacex.data.db.LaunchesEntity
import com.example.spacex.ui.screens.commons.ObserveAsEvents
import com.example.spacex.utils.TestTags
import org.koin.compose.viewmodel.koinViewModel

/**
 * [LaunchDetailsScreen] loads every state and data provided by
 * [LaunchDetailsViewModel] and creates an Observer for
 * [LaunchDetailsScreenEvent]
 */
@Composable
fun LaunchDetailsScreen(id: Int?) {
    val viewModel = koinViewModel<LaunchDetailsViewModel>()
    ObserveAsEvents(viewModel.eventChannelFlow) { event ->
        when (event) {
            is LaunchDetailsScreenEvent.FetchDetails -> {
                viewModel.getDetails(event.id)
            }

            is LaunchDetailsScreenEvent.YoutubeIntent -> {
                viewModel.openWebcast(event.id, event.webcast)
            }

            is LaunchDetailsScreenEvent.UrlIntent -> {
                viewModel.openArticle(event.url)
            }
        }
    }
    var dataFetched by rememberSaveable { mutableStateOf(false) }
    val details by viewModel.details.collectAsState()
    if (!dataFetched) {
        LaunchedEffect(Unit) {
            viewModel.postEvent(LaunchDetailsScreenEvent.FetchDetails(id ?: -1))
            dataFetched = true
        }
    }
    val ytCall = {
        viewModel.postEvent(LaunchDetailsScreenEvent.YoutubeIntent(
            details?.ytId ?: "", details?.ytWebcast ?: ""
        ))
    }
    val urlCall = { url: String ->
        viewModel.postEvent(LaunchDetailsScreenEvent.UrlIntent(url))
    }
    ComposableView(id ?: -1, details ?: LaunchesEntity(), ytCall, urlCall)
}

/**
 * [ComposableView] is the main composable that takes all the data loaded by
 * [LaunchDetailsScreen] and renders the screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposableView(id: Int, details: LaunchesEntity, ytCall: () -> Unit,
                   urlCall: (String) -> Unit) {
    Scaffold(modifier = Modifier
        .testTag(TestTags.DETAILS_ITEM_SCREEN),
        topBar = {
            TopAppBar(
                title = {
                    Text("Flight $id Details")
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onSecondary
                )
            )
        },
    ) { innerPadding ->
        val dataRows = listOf(
            "Mission" to details.missionName,
            "Rocket name" to details.rocketName,
            "Rocket type" to details.rocketType,
            "Launch site" to details.launchSite,
            "Flight details" to details.flightDetails
        )
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding)
            .height(900.dp)
            .background(MaterialTheme.colorScheme.background),
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(5.dp)) {
                item {
                    Box(modifier = Modifier
                        .fillParentMaxWidth()
                        .height(220.dp)) {
                        AsyncImage(modifier = Modifier
                            .size(180.dp)
                            .align(Alignment.Center),
                            model = details.largePatch,
                            contentDescription = "",
                            placeholder = painterResource(R.drawable.baseline_downloading_24)
                        )
                    }
                }
                itemsIndexed(dataRows) { index, item ->
                    val content = item.second ?: "N/A"
                    if (content.length > 70) {
                        DataRow(item.first, item.second ?: "N/A", 350.dp, true)
                    } else if (index == dataRows.size - 1) {
                        DataRow(item.first, item.second ?: "N/A", 80.dp, false)
                    } else {
                        DataRow(item.first, item.second ?: "N/A", 40.dp, false)
                    }
                }
                item {
                    LazyRow(modifier = Modifier
                        .fillParentMaxWidth(),
                        userScrollEnabled = false,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically) {
                        item {
                            Surface(modifier = Modifier
                                .size(100.dp),
                                color = Color.Transparent,
                                onClick = {urlCall(details.wikipedia ?: "")}) {
                                Image(painter = painterResource(
                                    id = R.drawable.wikipedia_logo),
                                    contentDescription = "")
                            }
                        }
                        item {
                            Surface(modifier = Modifier
                                .size(100.dp),
                                color = Color.Transparent,
                                onClick = ytCall) {
                                Image(painter = painterResource(
                                    id = R.drawable.youtube_logo),
                                    contentDescription = "")
                            }
                        }
                        item {
                            Surface(modifier = Modifier
                                .size(100.dp),
                                color = Color.Transparent,
                                onClick = {urlCall(details.article ?: "")}) {
                                Image(painter = painterResource(
                                    id = R.drawable.outline_article_24),
                                    contentDescription = "")
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * [DefaultPreview] for [LaunchDetailsScreen], is only used for Compose Preview
 */
@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    ComposableView(-1, LaunchesEntity(), {}, {})
}