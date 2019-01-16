package com.williamlu.gankio.api.test

import com.williamlu.datalib.bean.BaseBean
import com.williamlu.gankio.model.Movie
import io.reactivex.Observable
import retrofit2.http.*

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
    fun getMovieUSBox(@QueryMap map: Map<String, String>): Observable<BaseBean<*>>

    /**
     * 口碑榜
     */
    @FormUrlEncoded
    @POST("movie/weekly")
    fun getMovieWeekly(@Field("start") start: Int): Observable<BaseBean<*>>

    /**
     * 新片榜
     */
    @FormUrlEncoded
    @POST("movie/new_movies")
    fun getNewMovies(@FieldMap map: Map<String, String>): Observable<BaseBean<*>>

    /**
     * 正在上映 default: 北京
     */
    @GET("movie/new_movies/{start}")
    fun getMovieInTheaters(@Path("start") start: Int, @Query("city") city: String): Observable<BaseBean<*>>

    /**
     * 即将上映
     */
    @GET("movie/coming_soon")
    fun getMovieComingSoon(@Query("start") start: Int, @Query("count") count: Int): Observable<BaseBean<*>>

}