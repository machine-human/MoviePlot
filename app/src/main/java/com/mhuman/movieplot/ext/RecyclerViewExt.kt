package com.mhuman.movieplot.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mhuman.movieplot.base.BaseRecyclerViewAdapter

@BindingAdapter("bind:item")
fun RecyclerView.binItem(list: List<Nothing>?) {
    list?.let {
        (adapter as BaseRecyclerViewAdapter<*>).run {
            setList(list)
        }
    }
}