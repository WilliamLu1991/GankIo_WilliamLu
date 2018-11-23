package com.williamlu.gankio.home.view

import android.os.Bundle
import com.williamlu.gankio.R
import com.williamlu.gankio.base.BaseActivity
import com.williamlu.gankio.home.contract.MainContract
import com.williamlu.gankio.home.model.bean.Movie
import com.williamlu.gankio.home.presenter.MainPresenter

class MainActivity : BaseActivity(), MainContract.View {

    override fun setMainPresenter(presenter: MainContract.Presenter) {

    }

    override fun processComplete(data: List<Movie>) {

    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        MainPresenter(this)
    }

}
