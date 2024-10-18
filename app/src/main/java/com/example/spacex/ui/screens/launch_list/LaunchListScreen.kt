package com.example.spacex.ui.screens.launch_list

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.spacex.utils.Alert
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LaunchListScreen(navController: NavController) {
    val viewModel = koinViewModel<LaunchListViewModel>()
    viewModel.onSomething()
    Alert("LaunchListScreen")
    Text(
        text = "asdñflkjasdf"
    )
}

@Preview
@Composable
private fun DefaultPreview() {
    LaunchListScreen(rememberNavController())
}