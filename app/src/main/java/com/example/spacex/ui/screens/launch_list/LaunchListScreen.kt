package com.example.spacex.ui.screens.launch_list

import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.spacex.data.db.LaunchesEntity
import com.example.spacex.ui.screens.commons.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LaunchListScreen(navController: NavController) {
    val viewModel = koinViewModel<LaunchListViewModel>()
    ObserveAsEvents(viewModel.eventChannelFlow) { event ->
        when (event) {
            LaunchListScreenEvent.FetchData -> {
                viewModel.fetchData()
            }
        }
    }
    val flowLaunches by viewModel.launches.collectAsState()
    viewModel.postEvent(LaunchListScreenEvent.FetchData)
    ComposableView(navController, flowLaunches)
}

@Composable
fun ComposableView(navController: NavController, items: List<LaunchesEntity>) {
    LazyColumn (
        modifier = Modifier.safeContentPadding()
    ) {
        item {
            Text("Number of items: ${items.size}")
        }
        items(items) { item ->
            Text("item #${item.flightId}")
        }
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    ComposableView(rememberNavController(), listOf())
}