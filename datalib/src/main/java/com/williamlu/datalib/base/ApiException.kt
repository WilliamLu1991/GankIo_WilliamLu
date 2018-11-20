package com.williamlu.datalib.base

/*
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 
 */
class ApiException(var code: Int, msg: String) : IllegalArgumentException(msg)