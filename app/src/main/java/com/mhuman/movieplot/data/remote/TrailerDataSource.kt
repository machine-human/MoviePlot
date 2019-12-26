package com.mhuman.movieplot.data.remote

import com.mhuman.movieplot.network.model.TrailerKeyList
import io.reactivex.disposables.Disposable

interface TrailerDataSource {

    fun getTrailerKeyList(
        movieId: Int,
        success: (List<TrailerKeyList>) -> Unit,
        failed: () -> Unit
    ): Disposable
}