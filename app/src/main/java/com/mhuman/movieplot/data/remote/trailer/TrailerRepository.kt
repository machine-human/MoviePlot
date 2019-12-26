package com.mhuman.movieplot.data.remote.trailer

import com.mhuman.movieplot.data.remote.TrailerDataSource
import com.mhuman.movieplot.network.model.TrailerKeyList
import io.reactivex.disposables.Disposable

class TrailerRepository(
    private val dataSource: TrailerDataSource
) : TrailerDataSource {

    override fun getTrailerKeyList(
        movieId: Int,
        success: (List<TrailerKeyList>) -> Unit,
        failed: () -> Unit
    ): Disposable = dataSource.getTrailerKeyList(movieId, success, failed)
}