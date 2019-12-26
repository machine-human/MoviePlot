package com.mhuman.movieplot.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mhuman.movieplot.base.BaseViewModel
import com.mhuman.movieplot.data.local.favorite.FavoriteMovieRepository
import com.mhuman.movieplot.data.remote.cast.CastRepository
import com.mhuman.movieplot.data.remote.trailer.TrailerRepository
import com.mhuman.movieplot.network.model.CastInfoList
import com.mhuman.movieplot.network.model.MovieInfoList
import com.mhuman.movieplot.utils.SingleLiveEvent

class DetailViewModel(
    private val trailerRepository: TrailerRepository,
    private val castRepository: CastRepository,
    private val favoriteMovieRepository: FavoriteMovieRepository
) : BaseViewModel() {

    private val _liveEventForSaveFavoriteMovieInfo = SingleLiveEvent<Boolean>()
    val liveEventForSaveFavoriteMovieInfo: LiveData<Boolean> get() = _liveEventForSaveFavoriteMovieInfo

    private val _liveEventForCheckCastListSize = SingleLiveEvent<Boolean>()
    val liveEventForCheckCastListSize: LiveData<Boolean> get() = _liveEventForCheckCastListSize

    private val _liveTrailerKey = MutableLiveData<String>()
    val liveTrailerKey: LiveData<String> get() = _liveTrailerKey

    private val _liveCastList = MutableLiveData<List<CastInfoList>>()
    val liveCastList: LiveData<List<CastInfoList>> get() = _liveCastList

    fun saveFavoriteMovieInfo(
        movieInfoList: List<MovieInfoList>
    ) {
        addToDisposable(
            favoriteMovieRepository.saveFavoriteMovieList(
                movieInfoList,
                success = {
                    _liveEventForSaveFavoriteMovieInfo.value = true
                },
                failed = {
                    _liveEventForSaveFavoriteMovieInfo.value = false
                }
            )
        )
    }

    fun loadTrailerList(
        movieId: Int
    ) {
        startLoading()
        addToDisposable(
            trailerRepository.getTrailerKeyList(
                movieId,
                success = {
                    endLoading()
                    _liveTrailerKey.value = if (it.size == 0) "not-found" else it?.get(0).key
                },
                failed = {
                    endLoading()
                    handleErrorMessage("")
                    _liveTrailerKey.value = "not-found"
                })
        )
    }

    fun loadCastList(
        movieId: Int
    ) {
        addToDisposable(
            castRepository.getCastList(movieId,
                success = {
                    _liveCastList.value = it
                    if (it.size == 0)
                        _liveEventForCheckCastListSize.value = true
                },
                failed = {
                    handleErrorMessage("")
                })
        )
    }
}
