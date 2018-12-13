package com.williamlu.toolslib

import android.annotation.SuppressLint
import android.widget.TextView
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
class RxCountDownUtils(listener: onRxCountDownListener? = null) {
    private var sDisposable: Disposable? = null
    private var mListener: onRxCountDownListener? = listener

    interface onRxCountDownListener {
        fun onSubscribe(disposable: Disposable)
        fun onBefore()
        fun onNext(time: Int)
        fun onComplete()

    }

    fun countdown(time: Int) {
        var time = time

        if (time < 0) time = 0
        val countTime = time

        Observable.interval(0, 1, TimeUnit.SECONDS).map {
            countTime - it.toInt()
        }.take((countTime + 1).toLong()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
            mListener!!.onBefore()
        }.subscribe(object : io.reactivex.Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                sDisposable = d
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
        if (sDisposable != null) {
            sDisposable!!.dispose()
            sDisposable = null
        }
        if (mListener != null) {
            mListener = null
        }
    }
    /*fun countdown(time: Int, textView: TextView) {
        countdown(time, textView, -1, -1, -1, -1)
    }

    fun countdown(time: Int, textView: TextView, startBgResId: Int, endBgResId: Int, startTextColor: Int, endTextColor: Int) {
        var time = time
        mTextView = textView

        if (time < 0) time = 0
        val countTime = time

        Observable.interval(0, 1, TimeUnit.SECONDS).map {
            countTime - it.toInt()
        }.take((countTime + 1).toLong()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
            mTextView!!.isClickable = false

            if (startTextColor == -1) {
                mTextView!!.setTextColor(Color.GRAY)
            } else {
                mTextView!!.setTextColor(startTextColor)
            }

            if (startBgResId == -1) {
                mTextView!!.setBackgroundResource(R.drawable.shape_corners_bg_ffcccccc)
            } else {
                mTextView!!.setBackgroundResource(startBgResId)
            }
        }.subscribe(object : io.reactivex.Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                sDisposable = d
            }

            override fun onNext(t: Int) {
                mTextView!!.setText(t!!.toString() + " s")
            }

            override fun onComplete() {
                mTextView!!.text = "获取验证码"
                mTextView!!.isClickable = true

                if (endTextColor == -1) {
                    mTextView!!.setTextColor(Color.WHITE)
                } else {
                    mTextView!!.setTextColor(endTextColor)
                }

                if (endBgResId == -1) {
                    mTextView!!.setBackgroundResource(R.drawable.shape_corners_bg_ffff553a)
                } else {
                    mTextView!!.setBackgroundResource(endBgResId)
                }
                stopCountdown()
            }

            override fun onError(e: Throwable) {
                stopCountdown()
                ToastUtils.showToast(e.toString())
            }
        })

    }*/
}