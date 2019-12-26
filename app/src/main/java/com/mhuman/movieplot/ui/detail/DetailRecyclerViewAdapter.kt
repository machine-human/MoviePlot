package com.mhuman.movieplot.ui.detail

import android.view.ViewGroup
import com.mhuman.movieplot.base.BaseRecyclerViewAdapter
import com.mhuman.movieplot.network.model.CastInfoList

class DetailRecyclerViewAdapter : BaseRecyclerViewAdapter<CastInfoList>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailViewHolder = DetailViewHolder(parent)

}