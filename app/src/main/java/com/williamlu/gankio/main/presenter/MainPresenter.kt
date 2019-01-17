package com.williamlu.gankio.main.presenter

import android.text.TextUtils
import com.williamlu.datalib.base.ApiException
import com.williamlu.datalib.base.ApiObserver
import com.williamlu.datalib.bean.BaseBean
import com.williamlu.gankio.AppConstant
import com.williamlu.gankio.api.service.GankIoDataService
import com.williamlu.gankio.main.contract.MainContract
import com.williamlu.gankio.model.ClassifyDataBean
import com.williamlu.toolslib.ToastUtils

/**
 * @Author: WilliamLu
 * @Date: 2018/11/23
 * @Description:
 */
class MainPresenter(cView: MainContract.View) : MainContract.Presenter {
    private var mView: MainContract.View? = null

    init {
        mView = cView
        mView!!.setMainPresenter(this)
    }

    override fun getClassifyData(type: String, pageindex: String) {
        GankIoDataService.getInstance()
                .getClassifyData(type, AppConstant.ConfigConstant.SERVICE_PAGE_SIZE, pageindex)
                .subscribe(object : ApiObserver<BaseBean<List<ClassifyDataBean>>>() {
                    override fun onNext(t: BaseBean<List<ClassifyDataBean>>) {
                        if (t.results != null && t.results!!.size > 0) {
                            mView!!.processComplete(t.results!!)
                            mView!!.dismissAllView()
                        } else {
                            mView!!.showEmptyDataView()
                        }
                        mView!!.dismissLoadingDialog()
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        if (e is ApiException) {
                            if (!TextUtils.isEmpty(e.errmsg)) {
                                ToastUtils.showToast(e.errmsg)
                            }
                        }
                        mView!!.dismissLoadingDialog()
                        mView!!.showErrorView()
                    }
                })
    }

}