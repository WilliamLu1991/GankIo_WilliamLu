package com.williamlu.gankio.api.contract

import com.williamlu.datalib.bean.BaseBean
import io.reactivex.Observable

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
interface GankIoIData {

    fun getClassifyData(type: String, pagesize: String, pageindex: String): Observable<BaseBean<*>>

}