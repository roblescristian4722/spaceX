package com.example.spacex.ui.screens.launch_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.spacex.data.db.LaunchesEntity
import com.example.spacex.ui.screens.commons.ObserveAsEvents
import com.example.spacex.ui.screens.launch_list_item.LaunchListItemScreen
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
            LaunchListScreenEvent.FetchDataPullRefresh -> {
                viewModel.enablePullRefreshLoading()
                viewModel.fetchData()
            }
        }
    }
    var dataFetched by rememberSaveable { mutableStateOf(false) }
    val launches by viewModel.launches.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val pullRefreshLoading by viewModel.pullRefreshLoading.collectAsState()
    val pullRefreshState = rememberPullToRefreshState()
    val onRefresh = {
        viewModel.postEvent(LaunchListScreenEvent.FetchDataPullRefresh)
    }
    if (!dataFetched) {
        LaunchedEffect(Unit) {
            viewModel.postEvent(LaunchListScreenEvent.FetchData)
            dataFetched = true
        }
    }
    ComposableView(navController, launches, loading, pullRefreshState, pullRefreshLoading, onRefresh)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposableView(navController: NavController, items: List<LaunchesEntity>,
                   loading: Boolean, pullRefreshState: PullToRefreshState,
                   pullRefreshLoading: Boolean, onRefresh: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        PullToRefreshBox (
            state = pullRefreshState,
            isRefreshing = pullRefreshLoading,
            onRefresh = onRefresh,
        ) {
            if (loading && !pullRefreshLoading) {
                CircularProgressIndicator()
            } else {
                if (items.isNotEmpty()) {
                    LazyColumn (Modifier.safeContentPadding()
                    ) {
                        items(items) { item ->
                            LaunchListItemScreen(item)
                        }
                    }
                } else {
                    Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                        LazyColumn(Modifier.fillMaxHeight()) {}
                        Text(text = "No data available, " +
                                "connect to the internet and pull to refresh",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(start = 20.dp, end = 20.dp))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    ComposableView(rememberNavController(), listOf(), false, rememberPullToRefreshState(), false) {}
}