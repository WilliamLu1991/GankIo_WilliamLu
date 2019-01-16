package com.williamlu.datalib.base

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
class ApiException(var errorCode: Int, errmsg: String) : IllegalArgumentException(errmsg)