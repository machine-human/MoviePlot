package com.mhuman.movieplot.data.local.favorite

import com.mhuman.movieplot.data.local.FavoriteDataSource
import com.mhuman.movieplot.network.model.MovieInfoList
import io.reactivex.disposables.Disposable

class FavoriteMovieRepository(
    private val dataSource: FavoriteDataSource
) : FavoriteDataSource {

    override fun getFavoriteMovieList(
        success: (List<MovieInfoList>) -> Unit,
        failed: () -> Unit
    ): Disposable = dataSource.getFavoriteMovieList(success, failed)


    override fun saveFavoriteMovieList(
        movieInfo: List<MovieInfoList>,
        success: (List<MovieInfoList>) -> Unit,
        failed: () -> Unit
    ): Disposable = dataSource.saveFavoriteMovieList(movieInfo, success, failed)


    override fun deleteFavoriteMovieList(
        movieTitle: String?,
        success: () -> Unit,
        failed: () -> Unit
    ): Disposable = dataSource.deleteFavoriteMovieList(movieTitle, success, failed)


    override fun deleteAllFavoriteMovieList(
        success: () -> Unit,
        failed: () -> Unit
    ): Disposable = dataSource.deleteAllFavoriteMovieList(success, failed)

}