package com.williamlu.datalib.bean

import com.williamlu.datalib.DataConstant
import java.io.Serializable

/**
 * @Author: WilliamLu
 * @Data: 2019/1/8
 * @Description:
 */
class BaseBean<T> : Serializable {
    var code = DataConstant.ConfigConstant.SUCCESS_SERVER_CODE
    var msg = ""
    var data: T? = null

    var error = false
    var results: T? = null
}