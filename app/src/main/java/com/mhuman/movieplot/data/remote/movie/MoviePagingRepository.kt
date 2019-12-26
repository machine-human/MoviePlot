package com.mhuman.movieplot.data.remote.movie

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mhuman.movieplot.BuildConfig
import com.mhuman.movieplot.ext.applySingleScheduler
import com.mhuman.movieplot.network.api.MovieApi
import com.mhuman.movieplot.network.model.MovieInfoList
import com.mhuman.movieplot.network.model.MovieResult
import io.reactivex.disposables.Disposable
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MoviePagingRepository : KoinComponent {

    private val movieApi: MovieApi by inject()
    private val dataSourceFactory by lazy { MoviePagingFactory() }

    private val pageListConfig: PagedList.Config by lazy {
        PagedList.Config.Builder()
            .setPageSize(500)
            .setInitialLoadSizeHint(20)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(true)
            .build()
    }

    fun getAllMovieList(
        page: Int,
        success: (list: List<MovieInfoList>) -> Unit,
        failed: (error: Throwable) -> Unit
    ): Disposable =
        movieApi.getMainMovieList(
            BuildConfig.THE_MOVIE_API_KEY, page
        ).applySingleScheduler()
            .subscribe(
                { result ->
                    result.results?.let { success(it) }
                },
                { error ->
                    failed(error)
                })

    private val executor: Executor = Executors.newFixedThreadPool(5)

    fun requestAllmovieData(): MovieResult {

        val data = LivePagedListBuilder(dataSourceFactory, pageListConfig)
            .setFetchExecutor(executor)
            .build()

        val networkErrors = Transformations.switchMap(
            dataSourceFactory.liveMoviePagingData
        ) { dataSource -> dataSource.networkErrors }

        return MovieResult(data, networkErrors)
    }
}