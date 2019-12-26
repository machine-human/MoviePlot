package com.mhuman.movieplot.di

import com.mhuman.movieplot.data.remote.genre.GenrePagingRepository
import com.mhuman.movieplot.data.remote.movie.MoviePagingDataSource
import com.mhuman.movieplot.data.remote.movie.MoviePagingFactory
import com.mhuman.movieplot.data.remote.movie.MoviePagingRepository
import com.mhuman.movieplot.data.remote.search.SearchPagingRepository
import org.koin.dsl.module

val pagingModule = module {
    single { MoviePagingDataSource() }
    single { MoviePagingFactory() }
    single { SearchPagingRepository() }
    single { MoviePagingRepository() }
    single { GenrePagingRepository() }
}