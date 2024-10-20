package com.example.spacex.ui.screens.launch_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.spacex.ui.screens.commons.ObserveAsEvents
import com.example.spacex.utils.Alert
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LaunchListScreen(navController: NavController) {
    val viewModel = koinViewModel<LaunchListViewModel>()
    ObserveAsEvents(viewModel.eventChannelFlow) { event ->
        when (event) {
            LaunchListScreenEvent.Print -> {
                Alert("It works!!")
                viewModel.onSomething()
            }
        }
    }
    ComposableView(navController) {
        viewModel.postEvent(LaunchListScreenEvent.Print)
    }
}

@Composable
fun ComposableView(navController: NavController, onClick: () -> Unit) {
    LazyColumn (
        modifier = Modifier.safeContentPadding()
    ) {
        item {
            Button(
                onClick = onClick
            ) {
                Text(
                    text = "Press me!!"
                )
            }
        }
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    ComposableView(rememberNavController()) {}
}