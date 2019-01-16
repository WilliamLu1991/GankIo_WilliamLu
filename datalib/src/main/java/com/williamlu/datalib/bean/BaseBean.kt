package com.williamlu.datalib.bean

import java.io.Serializable

/**
 * @Author: WilliamLu
 * @Data: 2019/1/8
 * @Description:
 */
class BaseBean<T> : Serializable {
    var errorCode = 10000
    var errmsg = ""
    var data: T? = null
}