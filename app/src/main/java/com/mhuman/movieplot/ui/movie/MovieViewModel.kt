package com.mhuman.movieplot.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.mhuman.movieplot.base.BaseViewModel
import com.mhuman.movieplot.data.local.setting.SettingInfoRepository
import com.mhuman.movieplot.data.remote.genre.GenrePagingRepository
import com.mhuman.movieplot.data.remote.movie.MoviePagingRepository
import com.mhuman.movieplot.data.remote.search.SearchPagingRepository
import com.mhuman.movieplot.network.model.MovieInfoList
import com.mhuman.movieplot.network.model.MovieResult
import com.mhuman.movieplot.network.model.SettingInfoResult
import com.mhuman.movieplot.utils.SingleLiveEvent
import org.koin.core.KoinComponent

class MovieViewModel(
    private val movieRepository: MoviePagingRepository,
    private val searchRepository: SearchPagingRepository,
    private val genreRepository: GenrePagingRepository,
    private val settingInfoRepository: SettingInfoRepository
) : BaseViewModel(), KoinComponent {

    private val _liveEventIsDarkTheme = SingleLiveEvent<Boolean>()
    val liveEventIsDarkTheme: LiveData<Boolean> get() = _liveEventIsDarkTheme

    private val _liveSettingInfoList = SingleLiveEvent<List<SettingInfoResult>>()
    val liveSettingInfoResult: LiveData<List<SettingInfoResult>> get() = _liveSettingInfoList

    private val _liveGenreTitle = MutableLiveData<String>()
    val liveGenreTitle: LiveData<String> get() = _liveGenreTitle

    private val queryLiveData = MutableLiveData<String>()

    private var requestResult: LiveData<MovieResult> = Transformations.map(queryLiveData) {
        var query = queryLiveData.value?.split("&")
        when (query?.get(1)) {
            "SEARCH" -> searchRepository.searchMovieData(query?.get(0))
            "GENRE" -> genreRepository.requestGenreMovieData(query?.get(0))
            else -> movieRepository.requestAllmovieData()
        }
    }

    var liveMovieList: LiveData<PagedList<MovieInfoList>> = Transformations
        .switchMap(requestResult)
        {
            it.data
        }

    val liveNetworkErrors: LiveData<String> = Transformations
        .switchMap(requestResult)
        {
            it.netWorkErrors
        }

    fun loadMovieList() {
        startLoading()
        _liveGenreTitle.value = null
        queryLiveData.postValue("MAIN" + "&" + "ALL")
    }

    fun loadGenreMovieList(gnereTitle: String, genre: String) {
        startLoading()
        _liveGenreTitle.value = gnereTitle
        queryLiveData.postValue(genre + "&" + "GENRE")
    }

    fun loadSearchMovieList(title: String) {
        startLoading()
        _liveGenreTitle.value = null
        queryLiveData.postValue(title + "&" + "SEARCH")
    }

    fun getSettingInfo() {
        addToDisposable(
            settingInfoRepository.getSettingInfoList(
                success = {
                    _liveSettingInfoList.value = it
                    if (it.size != 0)
                        _liveEventIsDarkTheme.value = it.get(0).isDarkTheme
                },
                failed = { handleErrorMessage("") }
            )
        )
    }

    fun hideLoading() {
        endLoading()
    }

    fun getPagedListLiveData(): LiveData<PagedList<MovieInfoList>> {
        return liveMovieList
    }
}