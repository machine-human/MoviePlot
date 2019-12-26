package com.mhuman.movieplot.data.remote.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.mhuman.movieplot.network.model.MovieInfoList
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

class MoviePagingDataSource : PageKeyedDataSource<Int, MovieInfoList>(), KoinComponent {

    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }
    private val repository: MoviePagingRepository by inject()
    val networkErrors: MutableLiveData<String> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieInfoList>
    ) {
        compositeDisposable.add(
            repository.getAllMovieList(
                page = FIRST_PAGE,
                success = { result -> callback.onResult(result, null, FIRST_PAGE + 1) },
                failed = { networkErrors.postValue(ERROR_MESSAGE_REQUEST_FAILED) }
            )
        )
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieInfoList>
    ) {
        compositeDisposable.add(
            repository.getAllMovieList(
                page = params.key,
                success = { result -> callback.onResult(result, params.key + 1) },
                failed = { networkErrors.postValue(ERROR_MESSAGE_REQUEST_FAILED) }
            )
        )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieInfoList>
    ) {
        compositeDisposable.add(
            repository.getAllMovieList(
                page = params.key,
                success = { result -> callback.onResult(result, params.key - 1) },
                failed = { networkErrors.postValue(ERROR_MESSAGE_REQUEST_FAILED) }
            )
        )
    }

    companion object {
        const val FIRST_PAGE = 1
        const val ERROR_MESSAGE_REQUEST_FAILED = "데이터 요청을 실패하였습니다.\n네트워크 설정을 확인해주세요."
        const val ERROR_MESSAGE_EMPTY_DATA = "찾으시는 영화 데이터가 \n서버에 존재하지 않습니다."
    }
}

