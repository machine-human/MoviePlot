package com.mhuman.movieplot.di

import com.mhuman.movieplot.data.MovieRepository
import com.mhuman.movieplot.data.remote.MovieRemoteDataSource
import org.koin.dsl.module

val repositoryModule = module {
    single { MovieRemoteDataSource(get()) }
    single { MovieRepository(get()) }
}