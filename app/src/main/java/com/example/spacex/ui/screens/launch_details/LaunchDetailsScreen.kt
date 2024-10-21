package com.example.spacex.ui.screens.launch_details

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun LaunchDetailsScreen(navController: NavController, id: Int?) {
    ComposableView(id ?: -1)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposableView(id: Int) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Flight $id")
                }
            )
        }
    ) { innerPadding ->
        Text(modifier = Modifier.padding(innerPadding),
            text = "asdfñlkj")
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    ComposableView(-1)
}