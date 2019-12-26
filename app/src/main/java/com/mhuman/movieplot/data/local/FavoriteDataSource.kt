package com.mhuman.movieplot.data.local

import com.mhuman.movieplot.network.model.MovieInfoList
import io.reactivex.disposables.Disposable

interface FavoriteDataSource {

    fun getFavoriteMovieList(
        success: (List<MovieInfoList>) -> Unit,
        failed: () -> Unit
    ): Disposable

    fun saveFavoriteMovieList(
        movieInfo: List<MovieInfoList>,
        success: (List<MovieInfoList>) -> Unit,
        failed: () -> Unit
    ): Disposable

    fun deleteAllFavoriteMovieList(
        success: () -> Unit,
        failed: () -> Unit
    ): Disposable

    fun deleteFavoriteMovieList(
        movieTitle: String?,
        success: () -> Unit,
        failed: () -> Unit
    ): Disposable
}