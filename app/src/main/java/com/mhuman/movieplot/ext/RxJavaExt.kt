package com.mhuman.movieplot.ext

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.applySingleScheduler(): Single<T> =
    this.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())

fun Completable.applyCompletableScheduler(): Completable =
    this.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())