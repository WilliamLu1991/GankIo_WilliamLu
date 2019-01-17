package com.williamlu.gankio.main.contract

import com.williamlu.gankio.base.BaseLoadView
import com.williamlu.gankio.main.presenter.MainPresenter
import com.williamlu.gankio.model.ClassifyDataBean

/**
 * @Author: WilliamLu
 * @Date: 2018/11/23
 * @Description:
 */
interface MainContract {
    interface View : BaseLoadView {
        fun setMainPresenter(presenter: MainPresenter)

        fun processComplete(data: List<ClassifyDataBean>)

        //fun processError(msg: String)

    }

    interface Presenter {
        fun getClassifyData(type: String, pageindex: String)
    }
}