package com.mhuman.movieplot.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import org.jetbrains.anko.toast

abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes
    private val layoutResId: Int
) : AppCompatActivity() {

    protected lateinit var binding: B
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, layoutResId, null, false)
        binding.lifecycleOwner = this@BaseActivity
        setContentView(binding.root)
        initializeUI()
    }

    protected fun binding(action: B.() -> Unit) {
        binding.run(action)
    }

    protected fun showToastMessage(message: String) {
        toast(message)
    }

    abstract fun registerEvent()
    abstract fun initializeUI()
}