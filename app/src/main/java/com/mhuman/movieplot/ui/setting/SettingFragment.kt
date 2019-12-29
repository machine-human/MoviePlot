package com.mhuman.movieplot.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.mhuman.movieplot.BuildConfig
import com.mhuman.movieplot.R
import com.mhuman.movieplot.base.BaseFragment
import com.mhuman.movieplot.databinding.FragmentSettingBinding
import com.mhuman.movieplot.network.model.SettingInfoResult
import kotlinx.android.synthetic.main.fragment_setting.*
import org.jetbrains.anko.browse
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment :
    BaseFragment<FragmentSettingBinding, SettingViewModel>(R.layout.fragment_setting) {

    override val viewModel by viewModel<SettingViewModel>()
    private val settingInfoResult: MutableList<SettingInfoResult> = mutableListOf()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding {
            view = this@SettingFragment
        }
        registerEvent()
    }

    override fun initializeUI() {
        floating_button_send_opinion.setOnClickListener { context?.browse(BuildConfig.MY_APPLICATION_PLAY_STORE_URL) }
        floating_button_check_version.text = BuildConfig.VERSION_NAME
    }

    fun setThemeInfo(theme: Boolean) {
        if (settingInfoResult.size != 0) settingInfoResult.removeAt(0)

        settingInfoResult.add(SettingInfoResult(0, theme))
        viewModel.saveSettingInfo(settingInfoResult)
    }

    override fun registerEvent() {
        viewModel.liveEventForSaveSettingInfo.observe(this@SettingFragment, Observer {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                showToastMessage(getString(R.string.msg_apply_dark_theme))
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                showToastMessage(getString(R.string.msg_apply_light_theme))
            }
        })
    }
}