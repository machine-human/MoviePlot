package com.mhuman.movieplot.data.remote.genre

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mhuman.movieplot.network.model.MovieInfoList

class GenrePagingFactory(
    private val query: String
) : DataSource.Factory<Int, MovieInfoList>() {

    val liveGenrePagingData: MutableLiveData<GenrePagingDataSource> = MutableLiveData()
    private lateinit var dataSource: GenrePagingDataSource

    override fun create(): DataSource<Int, MovieInfoList> {
        dataSource = GenrePagingDataSource(query)
        liveGenrePagingData.postValue(dataSource)
        return dataSource
    }
}