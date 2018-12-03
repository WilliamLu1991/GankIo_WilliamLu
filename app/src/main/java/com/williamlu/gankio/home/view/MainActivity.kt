package com.williamlu.gankio.home.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.williamlu.gankio.R
import com.williamlu.gankio.base.BaseActivity
import com.williamlu.gankio.home.contract.MainContract
import com.williamlu.gankio.home.model.Movie
import com.williamlu.gankio.home.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
class MainActivity : BaseActivity(), MainContract.View {
    private var mMainPresenter: MainPresenter? = null
    private var mainAdapter: MainListAdapter? = null

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_main
    }

    override fun initPresenter() {
        MainPresenter(this)
    }

    override fun setMainPresenter(presenter: MainPresenter) {
        mMainPresenter = presenter
    }

    override fun processComplete(data: List<Movie>) {
        setMainListData(data)
        dismissSwipeRl(mSwipeRl)
    }

    private fun setMainListData(data: List<Movie>) {
        if (mainAdapter == null) {
            main_rv.layoutManager = LinearLayoutManager(this)
            mainAdapter = MainListAdapter(data)
            main_rv.adapter = mainAdapter
        } else {
            mainAdapter!!.setNewData(data)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        showLoadingView()
        mMainPresenter!!.getData()
        mSwipeRl.setOnRefreshListener {
            mMainPresenter!!.getData()
        }
    }

}
