package com.mhuman.movieplot.data.remote

import com.mhuman.movieplot.network.model.CastInfoList
import io.reactivex.disposables.Disposable

interface CastDataSource {

    fun getCastList(
        movieId: Int,
        success: (List<CastInfoList>) -> Unit,
        failed: () -> Unit
    ): Disposable
}