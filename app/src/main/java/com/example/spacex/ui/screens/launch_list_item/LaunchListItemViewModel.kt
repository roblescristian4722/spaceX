package com.example.spacex.ui.screens.launch_list_item

import android.graphics.drawable.Drawable
import com.example.spacex.data.db.LaunchesDao
import com.example.spacex.ui.screens.commons.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LaunchListItemViewModel(
    private val db: LaunchesDao
): BaseViewModel<LaunchListItemScreenEvent>() {
    private val _patch = MutableStateFlow<String?>(null)
    val patch = _patch.asStateFlow()

    fun drawImage(image: String?) {
        _patch.value = image
    }

    fun loadData(id: Int) {
        db.getByFlightIds(intArrayOf(id)).first().also {

        }
    }
}