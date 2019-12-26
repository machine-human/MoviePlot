package com.mhuman.movieplot.data.local.setting

import com.mhuman.movieplot.data.local.SettingDataSource
import com.mhuman.movieplot.ext.applyCompletableScheduler
import com.mhuman.movieplot.ext.applySingleScheduler
import com.mhuman.movieplot.network.model.SettingInfoResult
import io.reactivex.disposables.Disposable

class SettingInfoLocalDataSource(
    private val settingInfoDao: SettingInfoDao
) : SettingDataSource {

    override fun saveSettingInfo(
        settingInfo: List<SettingInfoResult>,
        success: () -> Unit,
        failed: () -> Unit
    ): Disposable =
        settingInfoDao.insertSettingInfo(settingInfo)
            .applyCompletableScheduler()
            .subscribe(
                {
                    success()
                }, {
                    failed()
                }
            )

    override fun getSettingInfoList(
        success: (List<SettingInfoResult>) -> Unit,
        failed: () -> Unit
    ): Disposable =
        settingInfoDao.getSettingInfo()
            .applySingleScheduler()
            .subscribe(
                { result ->
                    success(result)
                }, {
                    failed()
                }
            )
}