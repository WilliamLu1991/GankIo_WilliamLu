package com.williamlu.gankio.api.douban

import com.williamlu.datalib.base.DefaultTransformer
import com.williamlu.datalib.bean.BaseBean
import com.williamlu.gankio.api.BaseApiService
import com.williamlu.gankio.model.Movie
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

        fun getInstance(): DouBanMovieService {
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