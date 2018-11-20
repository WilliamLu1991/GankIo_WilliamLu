package com.williamlu.datalib.base

import com.williamlu.datalib.bean.BaseBean
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/*
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 
 */
class DefaultTransformer<T> : ObservableTransformer<T, T> {

    override fun apply(tObservable: Observable<T>): Observable<T> {
        return tObservable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { t ->
                    if ((t as BaseBean<T>).code !== 200) {
                        throw ApiException((t as BaseBean<T>).code!!, (t as BaseBean<T>).msg)
                    }
                    t
                }
    }
}