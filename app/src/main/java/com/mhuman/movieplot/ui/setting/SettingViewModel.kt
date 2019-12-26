package com.mhuman.movieplot.ui.setting

import androidx.lifecycle.LiveData
import com.mhuman.movieplot.base.BaseViewModel
import com.mhuman.movieplot.data.local.setting.SettingInfoRepository
import com.mhuman.movieplot.network.model.SettingInfoResult
import com.mhuman.movieplot.utils.SingleLiveEvent

class SettingViewModel(
    private val settingInfoRepository: SettingInfoRepository
) : BaseViewModel() {
    private val _liveEventForSaveSettingInfo = SingleLiveEvent<Boolean>()
    val liveEventForSaveSettingInfo: LiveData<Boolean> get() = _liveEventForSaveSettingInfo

    fun saveSettingInfo(settingInfoResult: List<SettingInfoResult>) {
        addToDisposable(
            settingInfoRepository.saveSettingInfo(settingInfoResult,
                success = {
                    if (settingInfoResult.get(0).isDarkTheme!!)
                        _liveEventForSaveSettingInfo.value = true
                    else
                        _liveEventForSaveSettingInfo.value = false
                },
                failed = { _liveEventForSaveSettingInfo.value = false }
            )
        )
    }
}