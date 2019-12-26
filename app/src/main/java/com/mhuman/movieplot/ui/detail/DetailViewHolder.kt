package com.mhuman.movieplot.ui.detail

import android.view.ViewGroup
import com.mhuman.movieplot.BuildConfig
import com.mhuman.movieplot.R
import com.mhuman.movieplot.base.BaseViewHolder
import com.mhuman.movieplot.databinding.ItemCastListBinding
import com.mhuman.movieplot.ext.loadUrl
import com.mhuman.movieplot.network.api.MovieApi
import com.mhuman.movieplot.network.model.CastInfoList
import org.jetbrains.anko.browse

class DetailViewHolder(
    parent: ViewGroup
) : BaseViewHolder<CastInfoList, ItemCastListBinding>(R.layout.item_cast_list, parent) {

    override fun bind(data: CastInfoList) {
        binding.run {
            imageViewCastProfile.loadUrl(MovieApi.API_BASE_IMAGE_PATH_URL + data.profilePath)
            cardViewPreview.setOnClickListener { it.context.browse(BuildConfig.NAVER_SEARCH_URL + data.name) }
        }
    }
}