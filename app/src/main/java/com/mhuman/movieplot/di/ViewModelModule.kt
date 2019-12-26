package com.mhuman.movieplot.di

import com.mhuman.movieplot.ui.detail.DetailViewModel
import com.mhuman.movieplot.ui.favorite.FavoriteViewModel
import com.mhuman.movieplot.ui.movie.MovieViewModel
import com.mhuman.movieplot.ui.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { FavoriteViewModel(get()) }
    viewModel { SettingViewModel(get()) }
    viewModel { DetailViewModel(get(), get(), get()) }
    viewModel { MovieViewModel(get(), get(), get(), get()) }
}