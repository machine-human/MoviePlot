package com.mhuman.movieplot.network.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class MovieResult(
    val data: LiveData<PagedList<MovieInfoList>>,
    val netWorkErrors: LiveData<String>?
)