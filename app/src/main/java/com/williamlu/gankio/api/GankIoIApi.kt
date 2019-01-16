package com.williamlu.gankio.api

import com.williamlu.datalib.bean.BaseBean
import com.williamlu.gankio.model.Movie
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
interface GankIoIApi {
    //获取分类数据  https://gank.io/api/data/福利/10/1
    @GET("data/{type}/{pagesize}/{pageindex}")
    fun getClassifyData(@Path("type") type: String, @Path("pagesize") pagesize: String, @Path("pageindex") pageindex: String): Observable<BaseBean<*>>

}