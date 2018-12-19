package com.williamlu.toolslib

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @Author: WilliamLu
 * @Date: 2018/11/21
 * @Description: 倒计时工具类
 */
class RxCountDownUtils {
    private var mDisposable: Disposable? = null
    private var mListener: onRxCountDownListener? = null

    interface onRxCountDownListener {
        fun onSubscribe(disposable: Disposable)
        fun onBefore()
        fun onNext(time: Int)
        fun onComplete()

    }

    fun countdown(time: Int, listener: onRxCountDownListener) {
        var time = time
        mListener = listener

        if (time < 0) time = 0
        val countTime = time

        Observable.interval(0, 1, TimeUnit.SECONDS).map {
            countTime - it.toInt()
        }.take((countTime + 1).toLong()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
            mListener!!.onBefore()
        }.subscribe(object : io.reactivex.Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                mDisposable = d
                mListener!!.onSubscribe(d)
            }

            override fun onNext(t: Int) {
                mListener!!.onNext(t)
            }

            override fun onComplete() {
                mListener!!.onComplete()
                stopCountdown()
            }

            override fun onError(e: Throwable) {
                stopCountdown()
                ToastUtils.showToast(e.toString())
            }
        })

    }

    fun stopCountdown() {
        if (mDisposable != null) {
            mDisposable!!.dispose()
            mDisposable = null
        }
    }
}