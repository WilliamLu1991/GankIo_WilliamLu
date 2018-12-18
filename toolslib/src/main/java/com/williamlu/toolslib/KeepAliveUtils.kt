package com.williamlu.toolslib

import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
class KeepAliveUtils {
    private var disposable: Disposable? = null
    private var mListener: onKeepAliveListener? = null

    interface onKeepAliveListener {
        fun onSubscribe(disposable: Disposable)
        fun onNext(t: Long)
    }

    /**
     * 开启保活
     */
    fun startKeepAlive(time: Int, listener: onKeepAliveListener) {
        mListener = listener
        Observable.interval(0, time.toLong(), TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Long> {
            override fun onSubscribe(d: Disposable) {
                disposable = d
                mListener!!.onSubscribe(d)
            }

            override fun onNext(t: Long) {
                //要调用的方法
                Logger.d("保活开始调用！" + DateUtils.getCurrentDateTime())
                mListener!!.onNext(t)
            }

            override fun onError(e: Throwable) {
                Logger.e("保活失败！" + e.toString())
                offKeepAlive()
            }

            override fun onComplete() {

            }
        })
    }

    /**
     * 关闭保活
     */
    fun offKeepAlive() {
        if (disposable != null) {
            disposable!!.dispose()
            disposable = null
        }
    }
}