package com.mhuman.movieplot.ui.movie

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.mhuman.movieplot.ui.favorite.FavoriteFragment
import com.mhuman.movieplot.ui.setting.SettingFragment

class MoviePagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> MovieFragment()
        1 -> FavoriteFragment()
        else -> SettingFragment()
    }

    override fun getCount() = 3
}