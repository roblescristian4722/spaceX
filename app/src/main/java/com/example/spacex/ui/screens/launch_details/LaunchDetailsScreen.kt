package com.example.spacex.ui.screens.launch_details

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.spacex.R
import com.example.spacex.data.db.LaunchesEntity
import com.example.spacex.ui.screens.commons.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LaunchDetailsScreen(navController: NavController, id: Int?) {
    val viewModel = koinViewModel<LaunchDetailsViewModel>()
    ObserveAsEvents(viewModel.eventChannelFlow) { event ->
        when (event) {
            is LaunchDetailsScreenEvent.FetchDetails -> {
                viewModel.getDetails(event.id)
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
    ComposableView(id ?: -1, details ?: LaunchesEntity())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposableView(id: Int, details: LaunchesEntity) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Flight $id")
                }
            )
        }
    ) { innerPadding ->
        ConstraintLayout(Modifier
            .padding(innerPadding)) {
            AsyncImage(modifier = Modifier
                .size(100.dp),
                model = details.largePatch,
                contentDescription = "Launch patch",
                placeholder = painterResource(R.drawable.ic_launcher_background))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    ComposableView(-1, LaunchesEntity())
}