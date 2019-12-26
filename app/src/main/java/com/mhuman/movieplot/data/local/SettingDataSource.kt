package com.mhuman.movieplot.data.local

import com.mhuman.movieplot.network.model.SettingInfoResult
import io.reactivex.disposables.Disposable

interface SettingDataSource {

    fun getSettingInfoList(
        success: (List<SettingInfoResult>) -> Unit,
        failed: () -> Unit
    ): Disposable

    fun saveSettingInfo(
        settingInfo: List<SettingInfoResult>,
        success: () -> Unit,
        failed: () -> Unit
    ): Disposable
}