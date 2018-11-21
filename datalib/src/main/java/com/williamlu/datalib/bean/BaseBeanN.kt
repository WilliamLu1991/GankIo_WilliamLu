package com.williamlu.datalib.bean

import java.io.Serializable

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 
 */
class BaseBeanN : Serializable {
    var code: Int? = 200
    var msg = ""
    var count: Int? = null
    var start: Int? = null
    var total: Int? = null
    var title = ""
    var date = ""
}