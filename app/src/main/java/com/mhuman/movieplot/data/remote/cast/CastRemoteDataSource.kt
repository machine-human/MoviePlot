package com.mhuman.movieplot.data.remote.cast

import com.mhuman.movieplot.BuildConfig
import com.mhuman.movieplot.data.remote.CastDataSource
import com.mhuman.movieplot.ext.applySingleScheduler
import com.mhuman.movieplot.network.api.MovieApi
import com.mhuman.movieplot.network.model.CastInfoList
import io.reactivex.disposables.Disposable

class CastRemoteDataSource(
    private val movieApi: MovieApi
) : CastDataSource {

    override fun getCastList(
        movieId: Int,
        success: (List<CastInfoList>) -> Unit,
        failed: () -> Unit
    ): Disposable =
        movieApi.getCastList(
            movieId,
            BuildConfig.THE_MOVIE_API_KEY
        )
            .applySingleScheduler()
            .subscribe(
                { result ->
                    result.cast?.let { success(it) }
                },
                { result ->
                    failed()
                })
}