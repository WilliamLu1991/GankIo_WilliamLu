package com.williamlu.gankio.home.model.api

import com.williamlu.datalib.bean.BaseBean
import com.williamlu.gankio.home.model.bean.Movie
import io.reactivex.Observable

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 
 */
interface DouBanIMovie {
    fun getMovieTop250(start: Int, count: Int): Observable<BaseBean<List<Movie>>>
}