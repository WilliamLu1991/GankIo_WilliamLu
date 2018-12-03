package com.williamlu.gankio.base

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.orhanobut.logger.Logger
import com.tbruyelle.rxpermissions2.RxPermissions
import com.williamlu.gankio.AppConstant
import com.williamlu.gankio.GankIoApplation
import com.williamlu.gankio.R
import com.williamlu.gankio.event.ExitAppEvent
import com.williamlu.widgetlib.dialog.CustomLoadingDialog
import com.williamlu.widgetlib.dialog.PermissionDialog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
abstract class BaseActivity : AppCompatActivity(), BaseLoadView {

    private var mLoadingDialog: Dialog? = null
    private var mLayoutEmptyLoading: RelativeLayout? = null
    private var mLayoutLlEmptyData: LinearLayout? = null
    private var mLayoutLlLoading: LinearLayout? = null
    private var mLayoutLlError: LinearLayout? = null

    /**
     * 获取布局ID
     */
    protected abstract fun getContentViewLayoutID(): Int

    /**
     * 设置需要的presenter
     */
    abstract fun initPresenter()

    /**
     * 初始化布局以及View控件
     */
    protected abstract fun initView(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLoadingDialog = CustomLoadingDialog.createLoadingDialog(this, AppConstant.DialogConstant.LOADING)
        EventBus.getDefault().register(this)
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID())
            initPresenter()
            initView(savedInstanceState)
        }
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        mLayoutEmptyLoading = findViewById<RelativeLayout>(R.id.layout_empty_loading)
        mLayoutLlEmptyData = findViewById<LinearLayout>(R.id.layout_ll_empty_data)
        mLayoutLlLoading = findViewById<LinearLayout>(R.id.layout_ll_loading)
        mLayoutLlError = findViewById<LinearLayout>(R.id.layout_ll_error)
    }

    /**
     * eventbus相应事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventbusListener(o: Any) {
        if (o is ExitAppEvent) {
            GankIoApplation.offNetworkReceiver()
            Logger.d(" 退出应用")
            ActivityCacheManager.getInstance().appExit(this)
        }
    }

    /**
     * 检查权限 及申请
     */
    @SuppressLint("CheckResult")
    private fun checkPermission(activity: Activity) {
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.BLUETOOTH).subscribe({ granted ->
            if (!granted) {
                PermissionDialog.showMissingPermissionDialog(activity)
            } else {

            }
        }, { throwable -> throwable.printStackTrace() })
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun showLoadingDialog() {
        mLoadingDialog!!.show()
    }

    override fun dismissLoadingDialog() {
        mLoadingDialog!!.dismiss()
    }

    override fun showLoadingView() {
        if (mLayoutEmptyLoading != null) {
            mLayoutEmptyLoading!!.visibility = View.VISIBLE
            mLayoutLlLoading!!.visibility = View.VISIBLE
            mLayoutLlEmptyData!!.visibility = View.GONE
            mLayoutLlError!!.visibility = View.GONE
        }
    }

    override fun showEmptyDataView() {
        if (mLayoutEmptyLoading != null) {
            mLayoutEmptyLoading!!.visibility = View.VISIBLE
            mLayoutLlLoading!!.visibility = View.GONE
            mLayoutLlEmptyData!!.visibility = View.VISIBLE
            mLayoutLlError!!.visibility = View.GONE
        }
    }

    override fun showErrorView() {
        if (mLayoutEmptyLoading != null) {
            mLayoutEmptyLoading!!.visibility = View.VISIBLE
            mLayoutLlLoading!!.visibility = View.GONE
            mLayoutLlEmptyData!!.visibility = View.GONE
            mLayoutLlError!!.visibility = View.VISIBLE
        }
    }

    override fun showEmptyView() {
        if (mLayoutEmptyLoading != null) {
            mLayoutEmptyLoading!!.visibility = View.VISIBLE
            mLayoutLlLoading!!.visibility = View.GONE
            mLayoutLlEmptyData!!.visibility = View.GONE
            mLayoutLlError!!.visibility = View.VISIBLE
        }
    }

    override fun dismissAllView() {
        if (mLayoutEmptyLoading != null) {
            mLayoutEmptyLoading!!.visibility = View.GONE
        }
    }

    override fun showSwipeRl(mSwipeRl: SwipeRefreshLayout) {
        if (mSwipeRl != null && !mSwipeRl!!.isRefreshing) {
            mSwipeRl!!.isRefreshing = true
        }
    }

    override fun dismissSwipeRl(mSwipeRl: SwipeRefreshLayout) {
        if (mSwipeRl != null && mSwipeRl!!.isRefreshing) {
            mSwipeRl!!.isRefreshing = false
        }
    }

}