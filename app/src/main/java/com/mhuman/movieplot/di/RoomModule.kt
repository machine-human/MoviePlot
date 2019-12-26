package com.mhuman.movieplot.di

import androidx.room.Room
import com.mhuman.movieplot.data.local.favorite.FavoriteMovieDataBase
import com.mhuman.movieplot.data.local.setting.SettingInfoDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            FavoriteMovieDataBase::class.java,
            "MovieInfoList.db"
        ).build()
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            SettingInfoDataBase::class.java,
            "SettingInfoResult.db"
        ).build()
    }

    single { get<FavoriteMovieDataBase>().FavoriteMovieDao() }
    single { get<SettingInfoDataBase>().SettingInfoDao() }
}