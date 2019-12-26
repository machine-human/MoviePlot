package com.mhuman.movieplot.data.remote.trailer

import com.mhuman.movieplot.BuildConfig
import com.mhuman.movieplot.data.remote.TrailerDataSource
import com.mhuman.movieplot.ext.applySingleScheduler
import com.mhuman.movieplot.network.api.MovieApi
import com.mhuman.movieplot.network.model.TrailerKeyList
import io.reactivex.disposables.Disposable

class TrailerRemoteDataSource(
    private val movieApi: MovieApi
) : TrailerDataSource {

    override fun getTrailerKeyList(
        movieId: Int,
        success: (List<TrailerKeyList>) -> Unit,
        failed: () -> Unit
    ): Disposable =
        movieApi.getTrailerKeyList(
            movieId,
            BuildConfig.THE_MOVIE_API_KEY
        )
            .applySingleScheduler()
            .subscribe(
                { result ->
                    result.results?.let(success)
                },
                { result ->
                    failed()
                })
}