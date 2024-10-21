package com.example.spacex.ui.screens.launch_details

import androidx.lifecycle.viewModelScope
import com.example.spacex.data.db.LaunchesDao
import com.example.spacex.data.db.LaunchesEntity
import com.example.spacex.ui.screens.commons.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LaunchDetailsViewModel(
    private val db: LaunchesDao
): BaseViewModel<LaunchDetailsScreenEvent>() {
    private val _details = MutableStateFlow<LaunchesEntity?>(null)
    val details = _details.asStateFlow()

    fun getDetails(id: Int) {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                if (id != -1) {
                    _details.value = db.getByFlightIds(intArrayOf(id)).first()
                }
            }
        }
    }
}