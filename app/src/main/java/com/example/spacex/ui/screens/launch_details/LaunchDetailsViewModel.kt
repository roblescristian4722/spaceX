package com.example.spacex.ui.screens.launch_details

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
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
    private val context: Context,
    private val db: LaunchesDao
): BaseViewModel<LaunchDetailsScreenEvent>() {
    private val _details = MutableStateFlow<LaunchesEntity?>(null)
    val details = _details.asStateFlow()

    fun openArticle(url: String) {
        viewModelScope.launch {
            val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(appIntent)
        }
    }

    fun openWebcast(id: String, webcast: String) {
        viewModelScope.launch {
            val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
            appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webcast))
            appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            try {
                context.startActivity(appIntent)
            } catch (ex: ActivityNotFoundException) {
                context.startActivity(webIntent)
            }
        }
    }

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