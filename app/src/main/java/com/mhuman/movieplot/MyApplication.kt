package com.mhuman.movieplot

import android.app.Application
import com.mhuman.movieplot.di.*
import com.mhuman.movieplot.network.api.MovieApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                listOf(
                    viewModelModule,
                    getNetworkModule(MovieApi.API_BASE_URL),
                    repositoryModule,
                    pagingModule,
                    roomModule
                )
            )
        }
    }
}