package com.mhuman.movieplot.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mhuman.movieplot.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    private val _liveToastEvent = SingleLiveEvent<String>()
    val liveTostEvent: LiveData<String> get() = _liveToastEvent

    private val _liveIsLoading = MutableLiveData<Boolean>(false)
    val liveIsLoading: LiveData<Boolean> get() = _liveIsLoading

    fun addToDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    protected fun handleErrorMessage(message: String?) {
        _liveToastEvent.value = if (message.equals("")) "데이터 요청을 실패하였습니다." else message
    }

    protected fun startLoading() {
        _liveIsLoading.value = true
    }

    protected fun endLoading() {
        _liveIsLoading.value = false
    }
}