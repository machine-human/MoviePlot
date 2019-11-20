package com.mhuman.movieplot.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T,B : ViewDataBinding>(
    @LayoutRes
    layoutResId: Int,
    parent: ViewGroup
): RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)) {
    protected var binding: B = DataBindingUtil.bind(itemView)!!

    abstract fun bind(data: T)
}