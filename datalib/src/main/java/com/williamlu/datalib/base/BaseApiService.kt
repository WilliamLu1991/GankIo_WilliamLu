package com.williamlu.datalib.base

import com.williamlu.datalib.DataConstant
import com.williamlu.datalib.RetrofitHelper
import com.williamlu.datalib.api.DouBanIApi

/*
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 
 */
open class BaseApiService {

    var mDouBanIApiService: DouBanIApi = RetrofitHelper.getApiService(DataConstant.getBaseUrl(), DouBanIApi::class.java, null)

}