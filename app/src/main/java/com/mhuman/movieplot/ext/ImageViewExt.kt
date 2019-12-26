package com.mhuman.movieplot.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("bind:loadUrl")
fun ImageView.loadUrl(url: String?) {
    url?.let {
        Glide.with(this)
            .load(it)
            .thumbnail(1f)
            .into(this)
    }
}

@BindingAdapter("bind:loadUrl")
fun ImageView.loadDrawable(image: Int) {
    image?.let {
        Glide.with(this)
            .load(image)
            .thumbnail(1f)
            .into(this)
    }
}