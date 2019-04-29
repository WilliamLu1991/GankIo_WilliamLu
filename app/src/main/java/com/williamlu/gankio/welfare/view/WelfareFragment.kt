package com.williamlu.gankio.main.view

import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.williamlu.gankio.R
import com.williamlu.gankio.base.GankIoBaseFragment
import com.williamlu.gankio.main.adapter.WelfareListAdapter
import com.williamlu.gankio.main.contract.MainContract
import com.williamlu.gankio.main.presenter.MainPresenter
import com.williamlu.gankio.model.ClassifyDataBean
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:首页
 */
class WelfareFragment : GankIoBaseFragment(), MainContract.View {
    private var mMainPresenter: MainPresenter? = null
    private var mWelfareAdapter: WelfareListAdapter? = null
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
        mMainPresenter!!.getClassifyData("福利", mPageIndex.toString())
    }

    private fun initRv(data: List<ClassifyDataBean>) {
        if (data != null) {
            if (mWelfareAdapter == null) {
                fragment_main_rv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                mWelfareAdapter = WelfareListAdapter(data)
                fragment_main_rv.adapter = mWelfareAdapter
                mWelfareAdapter!!.setOnLoadMoreListener {
                    mPageIndex += 1
                    mMainPresenter!!.getClassifyData("福利", mPageIndex.toString())
                }
            } else {
                if (mPageIndex == 1) {
                    mWelfareAdapter!!.setNewData(data)
                } else {
                    if (data.size > 0) {
                        mWelfareAdapter!!.addData(data)
                        mWelfareAdapter!!.loadMoreComplete()
                    } else {
                        mWelfareAdapter!!.loadMoreEnd()
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

       /* mSwipeRl.setOnRefreshListener {
            if (mWelfareAdapter != null) {
                mWelfareAdapter!!.setEnableLoadMore(false)
            }
            mSwipeRl.isRefreshing = false
            showLoadingView()
            mPageIndex = 1
            mMainPresenter!!.getClassifyData("福利", mPageIndex.toString())
        }*/
    }

}
