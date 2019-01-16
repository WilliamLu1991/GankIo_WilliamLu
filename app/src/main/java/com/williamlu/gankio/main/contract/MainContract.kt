package com.williamlu.gankio.main.contract

import com.williamlu.gankio.model.Movie
import com.williamlu.gankio.base.BaseLoadView
import com.williamlu.gankio.main.presenter.MainPresenter

/**
 * @Author: WilliamLu
 * @Date: 2018/11/23
 * @Description:
 */
interface MainContract {
    interface View : BaseLoadView {
        fun setMainPresenter(presenter: MainPresenter)

        fun processComplete(data: List<Movie>)

        fun processError(msg: String)

    }

    interface Presenter {
        fun getData()

    }
}