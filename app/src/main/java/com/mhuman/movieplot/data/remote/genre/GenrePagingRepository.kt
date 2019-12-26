package com.mhuman.movieplot.data.remote.genre

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

class GenrePagingRepository : KoinComponent {

    private val movieApi: MovieApi by inject()
    private lateinit var dataSourceFactory: GenrePagingFactory
    private val pageListConfig: PagedList.Config by lazy {
        PagedList.Config.Builder()
            .setPageSize(500)
            .setInitialLoadSizeHint(20)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(true)
            .build()
    }

    fun getGenreMovieList(
        query: String,
        page: Int,
        success: (list: List<MovieInfoList>) -> Unit,
        failed: (error: Throwable) -> Unit
    ): Disposable =
        movieApi.getGenreMovieList(
            BuildConfig.THE_MOVIE_API_KEY, page, query
        ).applySingleScheduler()
            .subscribe(
                { result ->
                    result.results?.let { success(it) }
                },
                { error ->
                    failed(error)
                })

    private val executor: Executor = Executors.newFixedThreadPool(5)

    fun requestGenreMovieData(query: String): MovieResult {

        dataSourceFactory = GenrePagingFactory(query)

        val data = LivePagedListBuilder(dataSourceFactory, pageListConfig)
            .setFetchExecutor(executor)
            .build()

        val networkErrors = Transformations.switchMap(
            dataSourceFactory.liveGenrePagingData
        ) { dataSource -> dataSource.networkErrors }

        return MovieResult(data, networkErrors)
    }
}