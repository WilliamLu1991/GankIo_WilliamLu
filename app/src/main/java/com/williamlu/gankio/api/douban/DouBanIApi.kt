package com.williamlu.gankio.api.douban

import com.williamlu.datalib.bean.BaseBean
import com.williamlu.datalib.bean.BaseBeanN
import com.williamlu.gankio.home.model.Movie
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 
 */
interface DouBanIApi {
    /**
     * 获取top250的影片信息
     */
    @GET("movie/top250")
    fun getMovieTop250(@Query("start") start: Int, @Query("count") count: Int): Observable<BaseBean<List<Movie>>>

    /**
     * 北美票房榜
     */
    @GET("movie/us_box")
    fun getMovieUSBox(): Observable<BaseBeanN>

    /**
     * 口碑榜
     */
    @GET("movie/weekly")
    fun getMovieWeekly(): Observable<BaseBeanN>

    /**
     * 新片榜
     */
    @GET("movie/new_movies")
    fun getNewMovies(): Observable<BaseBeanN>

    /**
     * 正在上映 default: 北京
     */
    @GET("movie/new_movies")
    fun getMovieInTheaters(@Query("city") city: String): Observable<BaseBeanN>

    /**
     * 即将上映
     */
    @GET("movie/coming_soon")
    fun getMovieComingSoon(@Query("start") start: Int, @Query("count") count: Int): Observable<BaseBeanN>

}