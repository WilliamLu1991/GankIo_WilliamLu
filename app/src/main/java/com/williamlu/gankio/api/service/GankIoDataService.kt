package com.williamlu.gankio.api.service

import com.williamlu.datalib.base.DefaultTransformer
import com.williamlu.datalib.bean.BaseBean
import com.williamlu.gankio.api.contract.GankIoIData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
class GankIoDataService private constructor() : BaseApiService(), GankIoIData {

    companion object {
        private var INSTANCE: GankIoDataService? = null

        fun getInstance(): GankIoDataService {
            synchronized(GankIoDataService::class.java) {
                if (INSTANCE == null) {
                    INSTANCE =
                            GankIoDataService()
                }
            }
            return INSTANCE!!
        }
    }

    override fun getClassifyData(type: String, pagesize: String, pageindex: String): Observable<BaseBean<*>> {

        return mGankIoApiService
                .getClassifyData(type, pagesize, pageindex)
                .compose(DefaultTransformer<BaseBean<*>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}