package com.mhuman.movieplot.ext

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibility")
fun View.setVisibility(isVisibility: Boolean) {
    visibility = if (isVisibility) View.VISIBLE else View.INVISIBLE
}