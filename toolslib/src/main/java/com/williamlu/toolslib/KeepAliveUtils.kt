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
class KeepAliveUtils private constructor() {

    private var disposable: Disposable? = null

    internal object HolderClass {
        var iodhSingle = KeepAliveUtils()
    }

    companion object {
        val STAET_TIME_SECONDS = 0
        val PERIOD_TIME_SECONDS = 30

        fun getInstance(): KeepAliveUtils {
            return HolderClass.iodhSingle
        }
    }

    /**
     * 开启保活
     */
    fun onKeepAlive() {
        offKeepAlive()
        Observable.interval(STAET_TIME_SECONDS.toLong(), PERIOD_TIME_SECONDS.toLong(), TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Long> {
                    override fun onNext(t: Long) {
                        //要调用的方法
                        Logger.d("保活开始调用！" + System.currentTimeMillis())
                    }

                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                    }

                    override fun onError(e: Throwable) {
                        Logger.e("保活失败！" + e.toString())
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