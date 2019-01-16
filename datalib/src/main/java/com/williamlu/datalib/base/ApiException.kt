package com.williamlu.datalib.base

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
class ApiException(errorCode: Int, errmsg: String) : IllegalArgumentException() {
    var errorCode: Int = errorCode
    var errmsg: String = errmsg
}