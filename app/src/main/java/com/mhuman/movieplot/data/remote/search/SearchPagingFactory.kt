package com.mhuman.movieplot.data.remote.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mhuman.movieplot.network.model.MovieInfoList

class SearchPagingFactory(
    private val query: String
) : DataSource.Factory<Int, MovieInfoList>() {

    val liveSearchPagingData: MutableLiveData<SearchPagingDataSource> = MutableLiveData()
    private lateinit var dataSource: SearchPagingDataSource

    override fun create(): DataSource<Int, MovieInfoList> {
        dataSource = SearchPagingDataSource(query)
        liveSearchPagingData.postValue(dataSource)
        return dataSource
    }

    fun getDataSource() = dataSource
}