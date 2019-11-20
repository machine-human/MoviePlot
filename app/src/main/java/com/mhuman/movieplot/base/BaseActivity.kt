package com.mhuman.movieplot.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.mhuman.movieplot.R

abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes
    private val layoutResId: Int
) : AppCompatActivity() {

    protected lateinit var binding: B
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, layoutResId, null, false)
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }

    protected fun binding(action: B.() -> Unit) {
        binding.run(action)
    }
}