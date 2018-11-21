package com.williamlu.datalib.api

import android.graphics.Movie
import com.williamlu.datalib.bean.BaseBean
import io.reactivex.Observable

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 
 */
interface DouBanIMovie {
    fun getMovieTop250(start: Int, count: Int): Observable<BaseBean<List<Movie>>>
}