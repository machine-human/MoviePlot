package com.mhuman.movieplot.ui.favorite

import android.view.ViewGroup
import com.mhuman.movieplot.base.BaseOnClickInterface
import com.mhuman.movieplot.base.BaseRecyclerViewAdapter
import com.mhuman.movieplot.base.BaseViewHolder
import com.mhuman.movieplot.network.model.MovieInfoList
import kotlinx.android.synthetic.main.item_movie_list.view.*

class FavoriteRecyclerViewAdapter(private val onClickInterface: BaseOnClickInterface) :
    BaseRecyclerViewAdapter<MovieInfoList>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder = FavoriteViewHolder(parent)

    override fun onBindViewHolder(
        holder: BaseViewHolder<MovieInfoList, *>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)

        holder.itemView.card_view_movie_poster.setOnLongClickListener {
            onClickInterface?.onItemLongSelected(it, position)
            true
        }

        holder.itemView.card_view_movie_overview.setOnLongClickListener {
            onClickInterface?.onItemLongSelected(it, position)
            true
        }
    }
}
