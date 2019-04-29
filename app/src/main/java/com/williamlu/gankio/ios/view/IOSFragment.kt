package com.williamlu.gankio.ios.view

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.williamlu.gankio.R
import com.williamlu.gankio.base.GankIoBaseFragment
import com.williamlu.gankio.main.adapter.IOSListAdapter
import com.williamlu.gankio.main.contract.MainContract
import com.williamlu.gankio.main.presenter.MainPresenter
import com.williamlu.gankio.model.ClassifyDataBean
import kotlinx.android.synthetic.main.fragment_main.*
/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:首页
 */
class IOSFragment : GankIoBaseFragment(), MainContract.View {
    private var mMainPresenter: MainPresenter? = null
    private var mIOSListAdapter: IOSListAdapter? = null
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
        mMainPresenter!!.getClassifyData("iOS", mPageIndex.toString())
    }

    private fun initRv(data: List<ClassifyDataBean>) {
        if (data != null) {
            if (mIOSListAdapter == null) {
                fragment_main_rv.layoutManager = LinearLayoutManager(context)
                mIOSListAdapter = IOSListAdapter(data)
                fragment_main_rv.adapter = mIOSListAdapter
                mIOSListAdapter!!.setOnLoadMoreListener {
                    mPageIndex += 1
                    mMainPresenter!!.getClassifyData("iOS", mPageIndex.toString())
                }
            } else {
                if (mPageIndex == 1) {
                    mIOSListAdapter!!.setNewData(data)
                } else {
                    if (data.size > 0) {
                        mIOSListAdapter!!.addData(data)
                        mIOSListAdapter!!.loadMoreComplete()
                    } else {
                        mIOSListAdapter!!.loadMoreEnd()
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
            if (mIOSListAdapter != null) {
                mIOSListAdapter!!.setEnableLoadMore(false)
            }
            mSwipeRl.isRefreshing = false
            showLoadingView()
            mPageIndex = 1
            mMainPresenter!!.getClassifyData("iOS", mPageIndex.toString())
        }*/
    }

}
