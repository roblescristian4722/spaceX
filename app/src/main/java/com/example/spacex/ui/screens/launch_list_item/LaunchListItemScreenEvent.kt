package com.example.spacex.ui.screens.launch_list_item

import android.graphics.drawable.Drawable

sealed class LaunchListItemScreenEvent {
    data class ImageLoaded(val image: Drawable): LaunchListItemScreenEvent()
}