package com.example.spacex.ui.screens.launch_details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.spacex.utils.Alert
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LaunchDetailsScreen(navController: NavController) {
    Alert("LaunchDetailsScreen")
    Text(
        text = "asdñflkjasdf"
    )
}

@Preview
@Composable
private fun DefaultPreview() {
    LaunchDetailsScreen(rememberNavController())
}