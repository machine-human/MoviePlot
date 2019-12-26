package com.mhuman.movieplot.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.mhuman.movieplot.R
import com.mhuman.movieplot.base.BaseActivity
import com.mhuman.movieplot.databinding.ActivityMainBinding
import com.mhuman.movieplot.ui.movie.MoviePagerAdapter
import com.mhuman.movieplot.ui.movie.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(
    R.layout.activity_main
) {
    private val viewModel by viewModel<MovieViewModel>()
    private var exitTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        registerEvent()
        viewModel.getSettingInfo()
        applyTheme()

        super.onCreate(savedInstanceState)

        binding {
            view_pager_main.adapter = MoviePagerAdapter(supportFragmentManager)
            view_pager_main.offscreenPageLimit = 3
        }

        view_pager_main.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) = Unit
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit
            override fun onPageSelected(position: Int) {
                bottom_navigation_main.menu.getItem(position).isChecked = true
            }
        })

        bottom_navigation_main.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_movie -> view_pager_main.currentItem = 0
                R.id.menu_favorite -> view_pager_main.currentItem = 1
                R.id.menu_setting -> view_pager_main.currentItem = 2
            }
            true
        }
    }

    private fun applyTheme() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppDarkTheme)
        else
            setTheme(R.style.AppLightTheme)
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            showToastMessage(getString(R.string.msg_description_back_finish))
            exitTime = System.currentTimeMillis()
        } else
            super.onBackPressed()
    }

    override fun registerEvent() {
        with(viewModel) {
            liveSettingInfoResult.observe(this@MainActivity, Observer {
                if (it.size == 0)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            })

            liveEventIsDarkTheme.observe(this@MainActivity, Observer {
                if (it)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            })
        }
    }
}
