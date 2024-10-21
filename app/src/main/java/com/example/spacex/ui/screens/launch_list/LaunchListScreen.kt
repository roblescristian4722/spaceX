package com.example.spacex.ui.screens.launch_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.spacex.data.db.LaunchesEntity
import com.example.spacex.ui.screens.commons.ObserveAsEvents
import com.example.spacex.ui.screens.launch_list_item.LaunchListItemScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LaunchListScreen(navController: NavController) {
    val viewModel = koinViewModel<LaunchListViewModel>()
    ObserveAsEvents(viewModel.eventChannelFlow) { event ->
        when (event) {
            LaunchListScreenEvent.FetchData -> {
                viewModel.enableLoadingScreen()
                viewModel.fetchData()
            }
            LaunchListScreenEvent.FinishedFetchingData -> {
                viewModel.disableLoadingScreen()
            }
        }
    }
    var dataFetched by rememberSaveable { mutableStateOf(false) }
    val flowLaunches by viewModel.launches.collectAsState()
    val flowLoading by viewModel.loading.collectAsState()
    if (!dataFetched) {
        LaunchedEffect(Unit) {
            viewModel.postEvent(LaunchListScreenEvent.FetchData)
            dataFetched = true
        }
    }
    ComposableView(navController, flowLaunches, flowLoading)
}

@Composable
fun ComposableView(navController: NavController, items: List<LaunchesEntity>,
                   loading: Boolean) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (loading) {
            CircularProgressIndicator()
        } else {
            LazyColumn (
                modifier = Modifier.safeContentPadding()
            ) {
                items(items) { item ->
                    LaunchListItemScreen(item)
                }
            }
        }
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    ComposableView(rememberNavController(), listOf(), false)
}