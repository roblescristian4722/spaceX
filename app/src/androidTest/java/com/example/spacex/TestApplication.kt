package com.example.spacex

import android.app.Application
import com.example.spacex.di.dbModule
import com.example.spacex.di.httpModule
import com.example.spacex.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TestApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TestApplication)
            modules(viewModelModule, httpModule, dbModule)
        }
    }
}