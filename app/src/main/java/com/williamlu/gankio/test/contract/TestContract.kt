package com.williamlu.gankio.test.contract

import com.williamlu.gankio.base.BaseLoadView
import com.williamlu.gankio.model.Movie
import com.williamlu.gankio.test.presenter.TestPresenter

/**
 * @Author: WilliamLu
 * @Date: 2018/11/23
 * @Description:
 */
interface TestContract {
    interface View : BaseLoadView {
        fun setTestPresenter(presenter: TestPresenter)

        fun processComplete(data: List<Movie>)

        fun processError(msg: String)

    }

    interface Presenter {
        fun getData()

    }
}