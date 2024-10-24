package com.example.spacex.ui.screens.launch_list

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.spacex.data.db.LaunchesEntity
import com.example.spacex.ui.screens.commons.ObserveAsEvents
import com.example.spacex.ui.screens.launch_list_item.LaunchListItemScreen
import org.koin.compose.viewmodel.koinViewModel

/**
 * [LaunchListScreen] loads every state and data provided by
 * [LaunchListViewModel] and creates an Observer for [LaunchListScreenEvent]
 */
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

@Composable
private fun GetScrollableByOrientation(navController: NavController,
                               items: List<LaunchesEntity>, orientation: Int,
                               innerPadding: PaddingValues) {
    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        LazyVerticalGrid(modifier = Modifier.padding(innerPadding),
            columns = GridCells.Adaptive(minSize = 300.dp)) {
            items(items) { item ->
                LaunchListItemScreen(navController, item)
            }
        }
    } else {
        LazyColumn (modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items) { item ->
                LaunchListItemScreen(navController, item)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoadedItems(navController: NavController, items: List<LaunchesEntity>) {
    if (items.isNotEmpty()) {
        Scaffold(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            topBar = {
                TopAppBar(title = {
                    Text("SpaceX Launches")
                },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onSecondary
                    ))
            }
        ) { innerPadding ->
            GetScrollableByOrientation(
                navController, items,
                LocalConfiguration.current.orientation, innerPadding)
        }
    } else {
        LazyColumn(Modifier.fillMaxSize()) {
            item {
                Text(text = "No data available, " +
                        "connect to the internet and pull to refresh",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .fillParentMaxSize()
                        .wrapContentHeight(align = Alignment.CenterVertically)
                        .padding(start = 20.dp, end = 20.dp))
            }
        }
    }
}

/**
 * [ComposableView] is the main composable that takes all the data loaded by
 * [LaunchListScreen] and renders the screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposableView(navController: NavController, items: List<LaunchesEntity>,
                   loading: Boolean, pullRefreshState: PullToRefreshState,
                   pullRefreshLoading: Boolean, onRefresh: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        PullToRefreshBox ( modifier = Modifier
            .fillMaxSize(),
            state = pullRefreshState,
            isRefreshing = pullRefreshLoading,
            onRefresh = onRefresh,
        ) {
            // When items are loading and pull refresh hasn't get called
            if (loading && !pullRefreshLoading) {
                Column (modifier = Modifier
                    .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            // When items got loaded
            } else {
                LoadedItems(navController, items)
            }
        }
    }
}

/**
 * [DefaultPreview] for [LaunchListScreen], is only used for Compose Preview
 */
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    ComposableView(rememberNavController(), listOf(), false,
            rememberPullToRefreshState(), false) {}
}