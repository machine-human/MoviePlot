package com.mhuman.movieplot.data.remote.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.mhuman.movieplot.data.remote.movie.MoviePagingDataSource.Companion.ERROR_MESSAGE_EMPTY_DATA
import com.mhuman.movieplot.data.remote.movie.MoviePagingDataSource.Companion.ERROR_MESSAGE_REQUEST_FAILED
import com.mhuman.movieplot.data.remote.movie.MoviePagingDataSource.Companion.FIRST_PAGE
import com.mhuman.movieplot.network.model.MovieInfoList
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

class SearchPagingDataSource(
    private val query: String
) : PageKeyedDataSource<Int, MovieInfoList>(), KoinComponent {

    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }
    private val repository: SearchPagingRepository by inject()
    val networkErrors: MutableLiveData<String> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieInfoList>
    ) {
        compositeDisposable.add(
            repository.getSearchMovieList(
                query = query, page = FIRST_PAGE,
                success =
                { result ->
                    if (!result.isNotEmpty())
                        networkErrors.postValue(ERROR_MESSAGE_EMPTY_DATA)
                    callback.onResult(result, null, FIRST_PAGE + 1)
                },
                failed = { networkErrors.postValue(ERROR_MESSAGE_REQUEST_FAILED) }
            )
        )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int,
                MovieInfoList>
    ) {
        compositeDisposable.add(
            repository.getSearchMovieList(
                query = query, page = params.key,
                success = { result -> callback.onResult(result, params.key - 1) },
                failed = { networkErrors.postValue(ERROR_MESSAGE_REQUEST_FAILED) }
            )
        )
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieInfoList>
    ) {
        compositeDisposable.add(
            repository.getSearchMovieList(
                query = query, page = params.key,
                success = { result -> callback.onResult(result, params.key + 1) },
                failed = { networkErrors.postValue(ERROR_MESSAGE_REQUEST_FAILED) }
            )
        )
    }
}

