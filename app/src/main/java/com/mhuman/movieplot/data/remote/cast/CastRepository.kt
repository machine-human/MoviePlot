package com.mhuman.movieplot.data.remote.cast

import com.mhuman.movieplot.data.remote.CastDataSource
import com.mhuman.movieplot.network.model.CastInfoList
import io.reactivex.disposables.Disposable

class CastRepository(
    private val dataSource: CastDataSource
) : CastDataSource {

    override fun getCastList(
        movieId: Int,
        success: (List<CastInfoList>) -> Unit,
        failed: () -> Unit
    ): Disposable = dataSource.getCastList(movieId, success, failed)
}