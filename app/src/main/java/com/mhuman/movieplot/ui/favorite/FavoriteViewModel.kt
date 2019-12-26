package com.mhuman.movieplot.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mhuman.movieplot.base.BaseViewModel
import com.mhuman.movieplot.data.local.favorite.FavoriteMovieRepository
import com.mhuman.movieplot.network.model.MovieInfoList
import com.mhuman.movieplot.utils.SingleLiveEvent

class FavoriteViewModel(
    private val favoriteMovieRepository: FavoriteMovieRepository
) : BaseViewModel() {

    private val _liveFavoriteMovieList = MutableLiveData<List<MovieInfoList>>()
    val liveFavoriteMovieList: LiveData<List<MovieInfoList>> get() = _liveFavoriteMovieList

    private val _liveEventForDeleteFavoriteMovieList = SingleLiveEvent<Boolean>()
    val liveEventForDeleteFavoriteMovieList: LiveData<Boolean> get() = _liveEventForDeleteFavoriteMovieList


    fun loadFavoriteMovieList() {
        addToDisposable(
            favoriteMovieRepository.getFavoriteMovieList(
                success = { _liveFavoriteMovieList.value = it },
                failed = { handleErrorMessage("") }
            )
        )
    }

    fun deleteAllFavoriteMovieList(
        adapter: FavoriteRecyclerViewAdapter
    ) {
        addToDisposable(
            favoriteMovieRepository.deleteAllFavoriteMovieList(
                success = {
                    adapter.clear()
                    _liveFavoriteMovieList.value = null
                    _liveEventForDeleteFavoriteMovieList.value = true
                },
                failed = { handleErrorMessage("") }
            )
        )
    }

    fun deleteFavoriteMovieList(
        adapter: FavoriteRecyclerViewAdapter,
        position: Int,
        movieTitle: String?
    ) {
        addToDisposable(
            favoriteMovieRepository.deleteFavoriteMovieList(
                movieTitle,
                success = {
                    adapter.remove(position)
                    if (adapter.itemCount == 0) _liveFavoriteMovieList.value = null
                    _liveEventForDeleteFavoriteMovieList.value = true
                },
                failed = { handleErrorMessage("") }
            )
        )
    }

    fun onClearedDisposable() {
        super.onCleared()
    }
}