package com.example.spacex.ui.screens.launch_list_item

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.AsyncImage
import com.example.spacex.R
import com.example.spacex.ui.screens.commons.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LaunchListItemScreen(id: Int) {
    val viewModel = koinViewModel<LaunchListItemViewModel>()
    ObserveAsEvents(viewModel.eventChannelFlow) { event ->
        when (event) {
            is LaunchListItemScreenEvent.ImageLoaded -> {
            }
        }
    }

    ComposableView(viewModel.patch.value)
}

@Composable
fun ComposableView(url: String?) {
    ConstraintLayout (Modifier
        .border(1.dp, Color.Black)
        .height(80.dp)
        .fillMaxWidth()) {
            val (image) = createRefs()
            AsyncImage(
                modifier = Modifier.border(1.dp, Color.Black).constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                },
                model = url,
                contentDescription = "Mission patch",
                placeholder = painterResource(R.drawable.ic_launcher_background)
            )
            Text("asdfñlkj")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposableView("")
}