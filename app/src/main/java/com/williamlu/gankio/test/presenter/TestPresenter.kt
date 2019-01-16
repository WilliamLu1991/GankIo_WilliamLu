package com.williamlu.gankio.test.presenter

import com.williamlu.datalib.base.ApiObserver
import com.williamlu.datalib.bean.BaseBean
import com.williamlu.gankio.api.test.DouBanApiService
import com.williamlu.gankio.model.Movie
import com.williamlu.gankio.test.contract.TestContract

/**
 * @Author: WilliamLu
 * @Date: 2018/11/23
 * @Description:
 */
class TestPresenter(cView: TestContract.View) : TestContract.Presenter {
    private var mView: TestContract.View? = null

    init {
        mView = cView
        mView!!.setTestPresenter(this)
    }

    override fun getData() {
        DouBanApiService.getInstance().getMovieTop250(1, 20).subscribe(object : ApiObserver<BaseBean<List<Movie>>>() {
            override fun onNext(t: BaseBean<List<Movie>>) {
                if (t == null) {
                    mView!!.showEmptyDataView()
                } else {
                    mView!!.processComplete(t.data as List<Movie>)
                    mView!!.dismissAllView()
                    mView!!.dismissLoadingDialog()
                }
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                mView!!.processError(e.toString())
                mView!!.showErrorView()
            }
        })
    }

}