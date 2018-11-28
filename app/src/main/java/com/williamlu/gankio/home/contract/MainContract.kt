package com.williamlu.gankio.home.contract

import com.williamlu.gankio.home.model.Movie
import com.williamlu.gankio.base.BaseLoadView
import com.williamlu.gankio.home.presenter.MainPresenter

/**
 * @Author: WilliamLu
 * @Date: 2018/11/23
 * @Description:
 */
interface MainContract {

    interface View : BaseLoadView {

        fun setMainPresenter(presenter: MainPresenter)

        fun processComplete(data: List<Movie>)

    }

    interface Presenter {

        fun getData()

    }
}