package com.williamlu.gankio.home.contract

import com.williamlu.gankio.home.model.bean.Movie
import com.williamlu.gankio.base.BaseLoadView

/**
 * @Author: WilliamLu
 * @Date: 2018/11/23
 * @Description:
 */
interface MainContract {

    interface View : BaseLoadView {

        fun setMainPresenter(presenter: Presenter)

        fun processComplete(data: List<Movie>)

    }

    interface Presenter {

        fun getData()

    }
}