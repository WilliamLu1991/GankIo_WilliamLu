package com.williamlu.gankio.api.service

import com.williamlu.datalib.RetrofitHelper
import com.williamlu.gankio.AppConstant
import com.williamlu.gankio.api.GankIoIApi
import com.williamlu.gankio.api.test.DouBanIApi

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 
 */
open class BaseApiService {

    var mDouBanApiService: DouBanIApi = RetrofitHelper.getApiService(AppConstant.getBaseUrl(), DouBanIApi::class.java)

    var mGankIoApiService: GankIoIApi = RetrofitHelper.getApiService(AppConstant.getBaseUrl(), GankIoIApi::class.java)

}