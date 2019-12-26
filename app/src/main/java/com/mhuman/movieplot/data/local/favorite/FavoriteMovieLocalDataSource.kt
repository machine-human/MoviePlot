package com.mhuman.movieplot.data.local.favorite

import com.mhuman.movieplot.data.local.FavoriteDataSource
import com.mhuman.movieplot.ext.applyCompletableScheduler
import com.mhuman.movieplot.ext.applySingleScheduler
import com.mhuman.movieplot.network.model.MovieInfoList
import io.reactivex.disposables.Disposable

class FavoriteMovieLocalDataSource(
    private val favoriteMovieDao: FavoriteMovieDao
) : FavoriteDataSource {

    override fun saveFavoriteMovieList(
        movieInfo: List<MovieInfoList>,
        success: (List<MovieInfoList>) -> Unit,
        failed: () -> Unit
    ): Disposable =
        favoriteMovieDao.insertFavoriteMovieInfo(movieInfo)
            .applyCompletableScheduler()
            .subscribe(
                {
                    success(movieInfo)
                }, {
                    failed()
                })

    override fun getFavoriteMovieList(
        success: (List<MovieInfoList>) -> Unit,
        failed: () -> Unit
    ): Disposable =
        favoriteMovieDao.getAllFavoriteMovieInfo()
            .applySingleScheduler()
            .subscribe(
                { result ->
                    success(result)
                }, {
                    failed()
                })

    override fun deleteFavoriteMovieList(
        movieTitle: String?,
        success: () -> Unit,
        failed: () -> Unit
    ): Disposable =
        favoriteMovieDao.deleteFavoriteMovieInfo(movieTitle)
            .applyCompletableScheduler()
            .subscribe(
                {
                    success()
                }, {
                    failed()
                })

    override fun deleteAllFavoriteMovieList(
        success: () -> Unit,
        failed: () -> Unit
    ): Disposable =
        favoriteMovieDao.deleteAllFavoriteMovieInfo()
            .applyCompletableScheduler()
            .subscribe(
                {
                    success()
                }, {
                    failed()
                })
}