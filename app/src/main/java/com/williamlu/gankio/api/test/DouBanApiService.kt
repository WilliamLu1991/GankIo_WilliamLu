package com.williamlu.gankio.api.test

import com.williamlu.datalib.base.DefaultTransformer
import com.williamlu.datalib.bean.BaseBean
import com.williamlu.gankio.api.service.BaseApiService
import com.williamlu.gankio.model.Movie
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
class DouBanApiService private constructor() : BaseApiService(), DouBanIMovie {

    companion object {
        private var INSTANCE: DouBanApiService? = null

        fun getInstance(): DouBanApiService {
            synchronized(DouBanApiService::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = DouBanApiService()
                }
            }
            return INSTANCE!!
        }
    }

    override fun getMovieTop250(start: Int, count: Int): Observable<BaseBean<List<Movie>>> {
        return mDouBanApiService
            .getMovieTop250(start, count)
            .compose(DefaultTransformer<BaseBean<List<Movie>>>())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}