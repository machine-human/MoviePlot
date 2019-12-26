package com.mhuman.movieplot.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mhuman.movieplot.R
import com.mhuman.movieplot.databinding.ItemMovieListBinding
import com.mhuman.movieplot.ext.loadDrawable
import com.mhuman.movieplot.ext.loadUrl
import com.mhuman.movieplot.network.api.MovieApi
import com.mhuman.movieplot.network.model.MovieInfoList
import com.mhuman.movieplot.ui.detail.DetailActivity
import org.jetbrains.anko.startActivity

class MoviePagingAdapter :
    PagedListAdapter<MovieInfoList, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemMovieListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_movie_list,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = getItem(position)
        user?.let {
            holder as ViewHolder
            holder.bind(it)
        }
    }


    class ViewHolder(private val item: ItemMovieListBinding) :

        RecyclerView.ViewHolder((item.root)) {
        fun bind(list: MovieInfoList) {
            setIsRecyclable(false)

            item.textViewTitleContent.text = list.title

            if (list.overview?.length == 0) {
                item.textViewMovieOverview.text = itemView.resources.getString(R.string.msg_empty_overview)
                item.imageViewMoreContent.isVisible = false
            } else
                item.textViewMovieOverview.text = list.overview

            if (list.posterPath == null)
                item.imageViewMoviePoster.loadDrawable(R.drawable.dummy_poster_image)
            else
                item.imageViewMoviePoster.loadUrl(MovieApi.API_BASE_IMAGE_PATH_URL + list.posterPath)


            item.cardViewMovieOverview.setOnClickListener {
                if (item.textViewMovieOverview.maxLines == 3) {
                    item.textViewMovieOverview.maxLines = android.R.attr.maxLines
                    item.imageViewMoreContent.loadDrawable(R.drawable.ic_keyboard_arrow_up_black_18dp)
                } else {
                    item.textViewMovieOverview.maxLines = 3
                    item.imageViewMoreContent.loadDrawable(R.drawable.ic_keyboard_arrow_down_black_18dp)
                }
            }

            item.cardViewMoviePoster.setOnClickListener {
                item.cardViewMoviePoster.context.startActivity<DetailActivity>(
                    DetailActivity.EXTRA_MOVIE_TITLE to list.title,
                    DetailActivity.EXTRA_MOVIE_OVERVIEW to list.overview,
                    DetailActivity.EXTRA_MOVIE_POSTER_PATH to list.posterPath,
                    DetailActivity.EXTRA_MOVIE_ID to list.id
                )
            }

        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<MovieInfoList>() {
            override fun areItemsTheSame(oldItem: MovieInfoList, newItem: MovieInfoList): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: MovieInfoList, newItem: MovieInfoList): Boolean = oldItem == newItem
        }
    }
}


