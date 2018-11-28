package com.williamlu.gankio.api

import com.williamlu.datalib.RetrofitHelper
import com.williamlu.gankio.AppConstant
import com.williamlu.gankio.api.douban.DouBanIApi

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 
 */
open class BaseApiService {

    var mDouBanIApiService: DouBanIApi = RetrofitHelper.getApiService(AppConstant.getBaseUrl(), DouBanIApi::class.java, null)

}