package com.williamlu.gankio.test.view

import android.Manifest
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import com.williamlu.gankio.AppConstant
import com.williamlu.gankio.R
import com.williamlu.gankio.base.GankIoBaseActivity
import com.williamlu.gankio.event.ExitAppEvent
import com.williamlu.gankio.model.Movie
import com.williamlu.gankio.test.adapter.TestListAdapter
import com.williamlu.gankio.test.contract.TestContract
import com.williamlu.gankio.test.presenter.TestPresenter
import com.williamlu.toolslib.ToastUtils
import com.williamlu.widgetlib.dialog.FullSheetDialog
import kotlinx.android.synthetic.main.activity_test.*
import org.greenrobot.eventbus.EventBus

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:首页
 */
class TestActivity : GankIoBaseActivity(), TestContract.View {
    private var mTestPresenter: TestPresenter? = null
    private var testAdapter: TestListAdapter? = null

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_test
    }

    override fun initPresenter() {
        TestPresenter(this)
    }

    override fun setTestPresenter(presenter: TestPresenter) {
        mTestPresenter = presenter
    }

    override fun processComplete(data: List<Movie>) {
        setTestListData(data)
        dismissSwipeRl()
    }

    override fun processError(msg: String) {
        dismissSwipeRl()
    }

    private fun setTestListData(data: List<Movie>) {
        if (testAdapter == null) {
            test_rv.layoutManager = LinearLayoutManager(this)
            testAdapter = TestListAdapter(data)
            test_rv.adapter = testAdapter
        } else {
            testAdapter!!.setNewData(data)
        }
    }

    override fun checkPermission(): Boolean {
        mPermissions = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.MODIFY_AUDIO_SETTINGS)
        return true
    }

    override fun initView() {
        mBaseToolBarHelper!!.showLeftView().setBgImg(R.drawable.lib_ic_personal)
        showLoadingView()
        mTestPresenter!!.getData()
    }

    override fun initListener() {
        mBaseToolBarHelper!!.getLeftView().setOnClickListener {
            val fullSheetDialog = FullSheetDialog(this)
            fullSheetDialog.setContentView(R.layout.activity_splash)
            fullSheetDialog.show()
        }
        mSwipeRl.setOnRefreshListener {
            mTestPresenter!!.getData()
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
