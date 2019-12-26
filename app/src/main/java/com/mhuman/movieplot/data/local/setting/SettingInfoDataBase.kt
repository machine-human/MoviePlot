package com.mhuman.movieplot.data.local.setting

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mhuman.movieplot.network.model.SettingInfoResult

@Database(entities = [SettingInfoResult::class], version = 1)
abstract class SettingInfoDataBase : RoomDatabase() {
    abstract fun SettingInfoDao(): SettingInfoDao
}