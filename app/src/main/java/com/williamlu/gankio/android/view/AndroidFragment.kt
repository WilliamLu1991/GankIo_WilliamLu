package com.williamlu.gankio.main.view

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.williamlu.gankio.R
import com.williamlu.gankio.base.GankIoBaseFragment
import com.williamlu.gankio.main.adapter.AndroidListAdapter
import com.williamlu.gankio.main.contract.MainContract
import com.williamlu.gankio.main.presenter.MainPresenter
import com.williamlu.gankio.model.ClassifyDataBean
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:首页
 */
class AndroidFragment : GankIoBaseFragment(), MainContract.View {
    private var mMainPresenter: MainPresenter? = null
    private var mAndroidAdapter: AndroidListAdapter? = null
    private var mPageIndex: Int = 1

    override fun getContentViewLayoutID(): Int {
        return R.layout.fragment_main
    }

    override fun initPresenter() {
        MainPresenter(this)
    }

    override fun setMainPresenter(presenter: MainPresenter) {
        mMainPresenter = presenter
    }

    override fun processComplete(data: List<ClassifyDataBean>) {
        initRv(data)
    }

    override fun initView(rootView: View?) {
        showLoadingView()
        mPageIndex = 1
        mMainPresenter!!.getClassifyData("Android", mPageIndex.toString())
    }

    private fun initRv(data: List<ClassifyDataBean>) {
        if (data != null) {
            if (mAndroidAdapter == null) {
                fragment_main_rv.layoutManager = LinearLayoutManager(context)
                mAndroidAdapter = AndroidListAdapter(data)
                fragment_main_rv.adapter = mAndroidAdapter
                mAndroidAdapter!!.setOnLoadMoreListener {
                    mPageIndex += 1
                    mMainPresenter!!.getClassifyData("Android", mPageIndex.toString())
                }
            } else {
                if (mPageIndex == 1) {
                    mAndroidAdapter!!.setNewData(data)
                } else {
                    if (data.size > 0) {
                        mAndroidAdapter!!.addData(data)
                        mAndroidAdapter!!.loadMoreComplete()
                    } else {
                        mAndroidAdapter!!.loadMoreEnd()
                    }
                }
            }
        } else {
            if (mPageIndex == 1) {
                showEmptyDataView()
            }
        }
    }

    override fun initListener() {

        /*mSwipeRl.setOnRefreshListener {
            if (mAndroidAdapter != null) {
                mAndroidAdapter!!.setEnableLoadMore(false)
            }
            mSwipeRl.isRefreshing = false
            showLoadingView()
            mPageIndex = 1
            mMainPresenter!!.getClassifyData("Android", mPageIndex.toString())
        }*/
    }

}
