package com.example.spacex.ui.screens.launch_list_item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import androidx.constraintlayout.compose.ChainStyle
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
    Surface(modifier = Modifier.height(120.dp),
        onClick = { onClick(item.flightId) },
        color = Color.Transparent) {
        ConstraintLayout (Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 2.dp)
            .border(
                border = BorderStroke(2.dp, Color.Black),
                shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp, topEnd = 10.dp)
            )
            .clip(RoundedCornerShape(bottomStart = 10.dp, topEnd = 10.dp, bottomEnd = 10.dp))
            .background(MaterialTheme.colorScheme.secondary),
        ) {
            val (image, flightId, missionName, rocketName, rocketType, launchSite) = createRefs()
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
                placeholder = painterResource(R.drawable.baseline_downloading_24)
            )
            Text(
                modifier = Modifier
                    .constrainAs(flightId) {
                        top.linkTo(parent.top, 20.dp)
                        start.linkTo(image.end, 20.dp)
                        bottom.linkTo(missionName.top)
                    },
                text = "Flight ${item.flightId}",
                fontSize = 12.sp
            )
            Text(
                modifier = Modifier
                    .constrainAs(missionName) {
                        top.linkTo(flightId.bottom)
                        start.linkTo(image.end, 20.dp)
                        bottom.linkTo(rocketName.top)
                    },
                text = "Mission ${item.missionName}",
                fontSize = 14.sp
            )
            Text(
                modifier = Modifier
                    .constrainAs(rocketName) {
                        top.linkTo(missionName.bottom)
                        start.linkTo(image.end, 20.dp)
                        bottom.linkTo(launchSite.top)
                    },
                text = "Rocket ${item.rocketName}",
                fontSize = 12.sp
            )
            Text(
                modifier = Modifier
                    .constrainAs(rocketType) {
                        top.linkTo(rocketName.top)
                        bottom.linkTo(rocketName.bottom)
                        start.linkTo(rocketName.end, 2.dp)
                    },
                text = "- ${item.rocketType}",
                fontSize = 12.sp
            )
            Text(
                modifier = Modifier
                    .constrainAs(launchSite) {
                        top.linkTo(rocketName.bottom)
                        start.linkTo(image.end, 20.dp)
                        bottom.linkTo(parent.bottom, 20.dp)
                    },
                text = item.launchSite,
                fontSize = 12.sp
            )
            createVerticalChain(
                flightId, missionName, rocketName, launchSite, chainStyle = ChainStyle.Packed)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    ComposableView(LaunchesEntity()) {}
}