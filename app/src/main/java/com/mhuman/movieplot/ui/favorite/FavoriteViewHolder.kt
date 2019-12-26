package com.mhuman.movieplot.ui.favorite

import android.view.ViewGroup
import androidx.core.view.isVisible
import com.mhuman.movieplot.R
import com.mhuman.movieplot.base.BaseViewHolder
import com.mhuman.movieplot.databinding.ItemMovieListBinding
import com.mhuman.movieplot.ext.loadDrawable
import com.mhuman.movieplot.ext.loadUrl
import com.mhuman.movieplot.network.api.MovieApi
import com.mhuman.movieplot.network.model.MovieInfoList
import com.mhuman.movieplot.ui.detail.DetailActivity
import org.jetbrains.anko.startActivity

class FavoriteViewHolder(
    parent: ViewGroup
) : BaseViewHolder<MovieInfoList, ItemMovieListBinding>(R.layout.item_movie_list, parent) {

    override fun bind(data: MovieInfoList) {
        binding.run {
            textViewTitleContent.text = data.title

            if (data.overview?.length == 0) {
                textViewMovieOverview.text = itemView.resources.getString(R.string.msg_empty_overview)
                imageViewMoreContent.isVisible = false
            } else
                textViewMovieOverview.text = data.overview

            if (data.posterPath == null)
                imageViewMoviePoster.loadDrawable(R.drawable.dummy_poster_image)
            else
                imageViewMoviePoster.loadUrl(MovieApi.API_BASE_IMAGE_PATH_URL + data.posterPath)

            cardViewMovieOverview.setOnClickListener {
                if (textViewMovieOverview.maxLines == 3) {
                    textViewMovieOverview.maxLines = android.R.attr.maxLines
                    imageViewMoreContent.loadDrawable(R.drawable.ic_keyboard_arrow_up_black_18dp)
                } else {
                    textViewMovieOverview.maxLines = 3
                    imageViewMoreContent.loadDrawable(R.drawable.ic_keyboard_arrow_down_black_18dp)
                }
            }

            cardViewMoviePoster.setOnClickListener {
                cardViewMoviePoster.context.startActivity<DetailActivity>(
                    DetailActivity.EXTRA_MOVIE_TITLE to data.title,
                    DetailActivity.EXTRA_MOVIE_OVERVIEW to data.overview,
                    DetailActivity.EXTRA_MOVIE_POSTER_PATH to data.posterPath,
                    DetailActivity.EXTRA_MOVIE_ID to data.id
                )
            }
        }
    }
}