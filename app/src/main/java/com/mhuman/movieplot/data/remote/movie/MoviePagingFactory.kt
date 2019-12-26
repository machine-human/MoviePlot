package com.mhuman.movieplot.data.remote.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mhuman.movieplot.network.model.MovieInfoList

class MoviePagingFactory : DataSource.Factory<Int, MovieInfoList>() {

    val liveMoviePagingData: MutableLiveData<MoviePagingDataSource> = MutableLiveData()
    private lateinit var dataSource: MoviePagingDataSource

    override fun create(): DataSource<Int, MovieInfoList> {
        dataSource = MoviePagingDataSource()
        liveMoviePagingData.postValue(dataSource)
        return dataSource
    }

    fun getDataSource() = dataSource
}