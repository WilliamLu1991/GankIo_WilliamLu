package com.williamlu.datalib

import com.williamlu.datalib.base.LogInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 
 */
object RetrofitHelper {

    private var mOkHttpClient: OkHttpClient? = null
    private val DEFAULT_TIMEOUT = 30
    private val DEFAULT_READ_TIMEOUT = 30

    init {
        initOkHttp()
    }

    private fun initOkHttp() {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        builder.readTimeout(DEFAULT_READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        builder.addInterceptor(LogInterceptor())
        mOkHttpClient = builder.build()
    }


    fun <T> getApiService(baseUrl: String, clz: Class<T>, client: OkHttpClient?): T {
        var client = client
        if (client == null) client = mOkHttpClient
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(clz)
    }
}