package com.example.spacex.ui.screens.launch_list_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.spacex.R
import com.example.spacex.data.db.LaunchesEntity
import com.example.spacex.ui.screens.commons.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LaunchListItemScreen(navController: NavController,item: LaunchesEntity) {
    val viewModel = koinViewModel<LaunchListItemViewModel>()
    ObserveAsEvents(viewModel.eventChannelFlow) { event ->
        when (event) {
            is LaunchListItemScreenEvent.ItemClicked -> {
                navController.navigate("details/${event.id}")
            }
        }
    }
    val onClick: (Int) -> Unit = { id ->
        viewModel.postEvent(LaunchListItemScreenEvent.ItemClicked(id))
    }

    ComposableView(item, onClick)
}

@Composable
fun ComposableView(item: LaunchesEntity, onClick: (Int) -> Unit) {
    Surface(modifier = Modifier.height(100.dp),
        onClick = { onClick(item.flightId) }) {
        ConstraintLayout (Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.LightGray)
            .fillMaxWidth()) {
            val (image, flightId, missionName, rocketName, ) = createRefs()
            AsyncImage(
                modifier = Modifier
                    .size(70.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start, 20.dp)
                    },
                model = item.smallPatch,
                contentDescription = "Mission patch",
                placeholder = painterResource(R.drawable.ic_launcher_background)
            )
            Text(
                modifier = Modifier
                    .constrainAs(flightId) {
                        top.linkTo(parent.top, 10.dp)
                        start.linkTo(image.end, 20.dp)
                    },
                text = "Flight ${item.flightId}",
                fontSize = 12.sp
            )
            Text(
                modifier = Modifier
                    .constrainAs(missionName) {
                        top.linkTo(flightId.bottom, 5.dp)
                        start.linkTo(image.end, 20.dp)
                    },
                text = "Mission ${item.missionName}"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposableView(LaunchesEntity(0, 0, "", "", "", "", "", "", "", "", "", "")) {}
}