package com.mhuman.movieplot.di

import com.mhuman.movieplot.data.local.FavoriteDataSource
import com.mhuman.movieplot.data.local.SettingDataSource
import com.mhuman.movieplot.data.local.favorite.FavoriteMovieLocalDataSource
import com.mhuman.movieplot.data.local.favorite.FavoriteMovieRepository
import com.mhuman.movieplot.data.local.setting.SettingInfoLocalDataSource
import com.mhuman.movieplot.data.local.setting.SettingInfoRepository
import com.mhuman.movieplot.data.remote.CastDataSource
import com.mhuman.movieplot.data.remote.TrailerDataSource
import com.mhuman.movieplot.data.remote.cast.CastRemoteDataSource
import com.mhuman.movieplot.data.remote.cast.CastRepository
import com.mhuman.movieplot.data.remote.trailer.TrailerRemoteDataSource
import com.mhuman.movieplot.data.remote.trailer.TrailerRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<TrailerDataSource> { TrailerRemoteDataSource(get()) }
    single { TrailerRepository(get()) }

    single<CastDataSource> { CastRemoteDataSource(get()) }
    single { CastRepository(get()) }

    single<FavoriteDataSource> { FavoriteMovieLocalDataSource(get()) }
    single { FavoriteMovieRepository(get()) }

    single<SettingDataSource> { SettingInfoLocalDataSource(get()) }
    single { SettingInfoRepository(get()) }
}