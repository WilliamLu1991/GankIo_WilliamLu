package com.williamlu.gankio.home.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import com.williamlu.gankio.AppConstant
import com.williamlu.gankio.R
import com.williamlu.gankio.base.GankBaseActivity
import com.williamlu.gankio.event.ExitAppEvent
import com.williamlu.gankio.home.contract.MainContract
import com.williamlu.gankio.home.model.Movie
import com.williamlu.gankio.home.presenter.MainPresenter
import com.williamlu.toolslib.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import com.williamlu.widgetlib.dialog.FullSheetDialog

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:首页
 */
class MainActivity : GankBaseActivity(), MainContract.View {
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
        dismissSwipeRl()
    }

    override fun processError(msg: String) {
        dismissSwipeRl()
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
        mBaseToolBarHelper!!.setBgImg(R.drawable.ic_personal, -1)
        showLoadingView()
        mMainPresenter!!.getData()
        mSwipeRl.setOnRefreshListener {
            mMainPresenter!!.getData()
        }
    }

    override fun initListener() {
        mBaseToolBarHelper!!.getLeftView().setOnClickListener {
            val fullSheetDialog = FullSheetDialog(this)
            fullSheetDialog.setContentView(R.layout.activity_splash)
            fullSheetDialog.show()
        }
    }

    private var exitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastUtils.showToast(AppConstant.ToastConstant.EXIT_APP)
                exitTime = System.currentTimeMillis()
            } else {
                ToastUtils.dismissToast()
                EventBus.getDefault().post(ExitAppEvent(this))
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
