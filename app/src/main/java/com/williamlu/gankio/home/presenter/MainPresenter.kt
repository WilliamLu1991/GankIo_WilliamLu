package com.williamlu.gankio.home.presenter

import com.williamlu.gankio.api.douban.DouBanMovieService
import com.williamlu.datalib.base.ApiObserver
import com.williamlu.datalib.bean.BaseBean
import com.williamlu.gankio.model.Movie
import com.williamlu.gankio.home.contract.MainContract

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

    override fun getData() {
        DouBanMovieService.getInstance().getMovieTop250(1, 20).subscribe(object : ApiObserver<BaseBean<List<Movie>>>() {
            override fun onNext(t: BaseBean<List<Movie>>) {
                if (t == null) {
                    mView!!.showEmptyDataView()
                } else {
                    mView!!.processComplete(t.subjects!!)
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