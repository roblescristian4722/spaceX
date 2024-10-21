package com.example.spacex.ui.screens.launch_details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun DataRow(header: String, content: String, height: Dp) {
    LazyRow (
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .height(height)
            .fillMaxWidth()
            .border(
                border = BorderStroke(2.dp, Color.Black),
                shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp, topEnd = 10.dp)
            ),
        horizontalArrangement = Arrangement.Start,
        userScrollEnabled = false) {
        item {
            ConstraintLayout(modifier = Modifier
                .fillParentMaxSize()) {
                val (left, right) = createRefs()
                Text(
                    modifier = Modifier
                        .constrainAs(left) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(right.start)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.percent(0.4f)
                        }
                        .clip(RoundedCornerShape(bottomStart = 10.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(start = 10.dp, end = 10.dp)
                        .height(height)
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    text = header,
                    fontSize = 16.sp
                )
                Text(
                    modifier = Modifier
                        .constrainAs(right) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            start.linkTo(left.end)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.percent(0.6f)
                        }
                        .clip(RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp))
                        .background(MaterialTheme.colorScheme.secondary)
                        .padding(start = 10.dp, end = 10.dp)
                        .height(height)
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    text = content,
                    fontSize = 14.sp
                )
            }
        }
    }
}
