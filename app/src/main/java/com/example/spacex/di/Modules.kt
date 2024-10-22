package com.example.spacex.di

import android.content.Context
import androidx.room.Room
import com.example.spacex.R
import com.example.spacex.data.db.AppDatabase
import com.example.spacex.data.network.def.SpaceXDefinitions
import com.example.spacex.data.network.impl.SpaceXImpl
import com.example.spacex.ui.screens.launch_details.LaunchDetailsViewModel
import com.example.spacex.ui.screens.launch_list.LaunchListViewModel
import com.example.spacex.ui.screens.launch_list_item.LaunchListItemViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val TIMEOUT: Long = 10

fun provideHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .build()
}

fun provideConverterFactory(): GsonConverterFactory {
    val gson = GsonBuilder().setLenient().create()
    return GsonConverterFactory.create(gson)
}

fun provideRetrofit(
    context: Context,
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory): Retrofit {
    return Retrofit.Builder()
        .baseUrl(context.getString(R.string.baseUrl))
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()
}

fun provideService(retrofit: Retrofit): SpaceXDefinitions {
    return retrofit.create(SpaceXDefinitions::class.java)
}

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidContext().applicationContext ?: androidContext(),
            AppDatabase::class.java,
            "MainDatabase")
            .build()
            .launchesDao()
    }
}

val httpModule = module {
    single { provideHttpClient() }
    single { provideConverterFactory() }
    single { provideRetrofit(androidContext(), get(), get()) }
    single { provideService(get()) }

    single { SpaceXImpl(get()) }
}

val viewModelModule = module {
    viewModel { LaunchDetailsViewModel(androidContext(), get()) }
    viewModel { LaunchListViewModel(get(), get()) }
    viewModel { LaunchListItemViewModel() }
}