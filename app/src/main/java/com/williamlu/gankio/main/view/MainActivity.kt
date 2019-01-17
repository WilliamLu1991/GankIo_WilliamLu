package com.williamlu.gankio.main.view

import android.Manifest
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.KeyEvent
import com.chad.library.adapter.base.BaseQuickAdapter
import com.williamlu.gankio.R
import com.williamlu.gankio.base.GankIoBaseActivity
import com.williamlu.gankio.event.ExitAppEvent
import com.williamlu.gankio.main.adapter.MainListAdapter
import com.williamlu.gankio.main.contract.MainContract
import com.williamlu.gankio.main.presenter.MainPresenter
import com.williamlu.gankio.model.ClassifyDataBean
import com.williamlu.toolslib.ToastUtils
import com.williamlu.widgetlib.dialog.CustomAlertDialog
import com.williamlu.widgetlib.dialog.FullSheetDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:首页
 */
class MainActivity : GankIoBaseActivity(), MainContract.View {
    private var mMainPresenter: MainPresenter? = null
    private var mMainAdapter: MainListAdapter? = null
    private var mPageIndex: Int = 1

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_main
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

    override fun initView() {
        mBaseToolBarHelper!!.setTitleName(resources.getString(R.string.app_name)).showLeftView().setBgImg(R.drawable.lib_ic_personal)
        showLoadingView()
        mPageIndex = 1
        mMainPresenter!!.getClassifyData("福利", mPageIndex.toString())
    }

    private fun initRv(data: List<ClassifyDataBean>) {
        if (data != null) {
            if (mMainAdapter == null) {
                main_rv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                mMainAdapter = MainListAdapter(data)
                main_rv.adapter = mMainAdapter
                mMainAdapter!!.setOnLoadMoreListener {
                    mPageIndex += 1
                    mMainPresenter!!.getClassifyData("福利", mPageIndex.toString())
                }
            } else {
                if (mPageIndex == 1) {
                    mMainAdapter!!.setNewData(data)
                } else {
                    if (data.size > 0) {
                        mMainAdapter!!.addData(data)
                        mMainAdapter!!.loadMoreComplete()
                    } else {
                        mMainAdapter!!.loadMoreEnd()
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
        mBaseToolBarHelper!!.getLeftView().setOnClickListener {
            val fullSheetDialog = FullSheetDialog(this)
            fullSheetDialog.setContentView(R.layout.view_mine)
            fullSheetDialog.show()
        }

        mSwipeRl.setOnRefreshListener {
            if (mMainAdapter != null) {
                mMainAdapter!!.setEnableLoadMore(false)
            }
            mSwipeRl.isRefreshing = false
            showLoadingView()
            mPageIndex = 1
            mMainPresenter!!.getClassifyData("福利", mPageIndex.toString())
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {

            CustomAlertDialog.getInstance()
                    .createAlertDialog(this, "确定要退出应用吗", "再看看", "狠心退出", true, false,
                            object : CustomAlertDialog.OnDialogSelectListener {
                                override fun onRightSelect() {
                                    ToastUtils.dismissToast()
                                    EventBus.getDefault().post(ExitAppEvent(this@MainActivity))
                                }

                            })

            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
