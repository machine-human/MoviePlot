package com.mhuman.movieplot.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

abstract class BaseFragment<B : ViewDataBinding, VM : ViewModel>(
    @LayoutRes
    private val layoutResId: Int
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

    protected fun showToastMessage(message: String) {
        context?.toast(message)
    }

    protected fun showAlertDialog(title: String, action: () -> Unit) {
        context?.alert(title) {
            this.yesButton { action() }
        }?.show()
    }

    abstract fun registerEvent()
}