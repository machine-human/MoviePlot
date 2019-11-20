package com.mhuman.movieplot.di

import com.mhuman.movieplot.ui.category.CategoryViewModel
import com.mhuman.movieplot.ui.favorite.FavoriteViewModel
import com.mhuman.movieplot.ui.movie.MovieViewModel
import com.mhuman.movieplot.ui.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { CategoryViewModel() }
    viewModel { FavoriteViewModel() }
    viewModel { SettingViewModel() }
}