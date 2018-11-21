package com.williamlu.datalib.api

import android.graphics.Movie
import com.williamlu.datalib.base.BaseApiService
import com.williamlu.datalib.base.DefaultTransformer
import com.williamlu.datalib.bean.BaseBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 
 */
class DouBanMovieService : BaseApiService(), DouBanIMovie {

    companion object {
        private var INSTANCE: DouBanMovieService? = null

        val instance: DouBanMovieService
            get() {
                synchronized(DouBanMovieService::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = DouBanMovieService()
                    }
                }
                return INSTANCE!!
            }
    }


    override fun getMovieTop250(start: Int, count: Int): Observable<BaseBean<List<Movie>>> {
        return mDouBanIApiService
                .getMovieTop250(start, count)
                .compose(DefaultTransformer<BaseBean<List<Movie>>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}