package com.mhuman.movieplot.network.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "settingInfo", primaryKeys = ["id"])
data class SettingInfoResult(
    val id: Int = 0,

    @SerializedName("isDarkTheme")
    val isDarkTheme: Boolean?
)