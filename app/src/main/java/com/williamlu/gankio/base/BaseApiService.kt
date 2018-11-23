package com.williamlu.gankio.base

import com.williamlu.datalib.RetrofitHelper
import com.williamlu.gankio.AppConstant
import com.williamlu.gankio.home.model.api.DouBanIApi

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 
 */
open class BaseApiService {

    var mDouBanIApiService: DouBanIApi = RetrofitHelper.getApiService(AppConstant.getBaseUrl(), DouBanIApi::class.java, null)

}