package com.mhuman.movieplot.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.mhuman.movieplot.BR

abstract class BaseFragment<B : ViewDataBinding, VM : ViewModel>(
    @LayoutRes private val layoutResId: Int
) : Fragment() {

    protected lateinit var binding: B
        private set

    abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding {
            binding.lifecycleOwner = this@BaseFragment
            binding.setVariable(BR.vm, viewModel)
        }
    }

    protected fun binding(action: B.() -> Unit) {
        binding.run(action)
    }
}