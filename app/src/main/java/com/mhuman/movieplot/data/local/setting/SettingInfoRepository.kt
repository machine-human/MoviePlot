package com.mhuman.movieplot.data.local.setting

import com.mhuman.movieplot.data.local.SettingDataSource
import com.mhuman.movieplot.network.model.SettingInfoResult
import io.reactivex.disposables.Disposable

class SettingInfoRepository(
    private val dataSource: SettingDataSource
) : SettingDataSource {

    override fun saveSettingInfo(
        settingInfo: List<SettingInfoResult>,
        success: () -> Unit,
        failed: () -> Unit
    ): Disposable = dataSource.saveSettingInfo(settingInfo, success, failed)

    override fun getSettingInfoList(
        success: (List<SettingInfoResult>) -> Unit,
        failed: () -> Unit
    ): Disposable = dataSource.getSettingInfoList(success, failed)

}