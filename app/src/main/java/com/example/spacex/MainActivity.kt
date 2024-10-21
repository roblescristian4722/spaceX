package com.example.spacex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import com.example.spacex.ui.navigation.NavEntry
import com.example.spacex.ui.theme.SpaceXTheme
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinContext {
                SpaceXTheme(dynamicColor = false) {
                    Surface(modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)) {
                    }
                    NavEntry()
                }
            }
        }
    }
}