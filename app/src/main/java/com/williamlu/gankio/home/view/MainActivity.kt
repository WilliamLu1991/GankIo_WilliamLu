package com.williamlu.gankio.home.view

import android.os.Bundle
import com.williamlu.gankio.R
import com.williamlu.gankio.base.BaseActivity
import com.williamlu.gankio.home.contract.MainContract
import com.williamlu.gankio.home.model.Movie
import com.williamlu.gankio.home.presenter.MainPresenter

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
class MainActivity : BaseActivity(), MainContract.View {
    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_main
    }

    override fun initPresenter() {
        MainPresenter(this)
    }

    private var mMainPresenter: MainPresenter? = null

    override fun setMainPresenter(presenter: MainPresenter) {
        mMainPresenter = presenter
    }

    override fun processComplete(data: List<Movie>) {

    }

    override fun initView(savedInstanceState: Bundle?) {
        mMainPresenter!!.getData()
    }

}
