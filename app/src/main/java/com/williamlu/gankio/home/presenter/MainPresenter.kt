package com.williamlu.gankio.home.presenter

import com.williamlu.gankio.home.model.api.DouBanMovieService
import com.williamlu.datalib.base.ApiObserver
import com.williamlu.datalib.bean.BaseBean
import com.williamlu.gankio.home.model.bean.Movie
import com.williamlu.gankio.home.contract.MainContract

/**
 * @Author: WilliamLu
 * @Date: 2018/11/23
 * @Description:
 */
class MainPresenter(mView: MainContract.View) : MainContract.Presenter {

    private val mView: MainContract.View

    init {
        this.mView = mView
        this.mView.setMainPresenter(this)
    }

    override fun getData() {
        DouBanMovieService.getInstance()
                .getMovieTop250(1, 20)
                .subscribe(object : ApiObserver<BaseBean<List<Movie>>>() {
                    override fun onNext(t: BaseBean<List<Movie>>) {
                        mView.processComplete(t.subjects!!)
                    }
                })
    }
}