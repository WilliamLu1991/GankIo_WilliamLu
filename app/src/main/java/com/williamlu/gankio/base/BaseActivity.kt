package com.williamlu.gankio.base

import android.app.Dialog
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.williamlu.datalib.bean.ApiExceptionEvent
import com.williamlu.datalib.bean.ServerExceptionEvent
import com.williamlu.gankio.AppConstant
import com.williamlu.gankio.GankIoApplation
import com.williamlu.gankio.R
import com.williamlu.gankio.event.ExitAppEvent
import com.williamlu.toolslib.PermissionsUtils
import com.williamlu.toolslib.SpUtils
import com.williamlu.toolslib.ToastUtils
import com.williamlu.widgetlib.dialog.BaseToolBarHelper
import com.williamlu.widgetlib.dialog.CustomLoadingDialog
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
abstract class BaseActivity : AppCompatActivity(), BaseLoadView {
    private val mSpUtils = SpUtils.getInstance(AppConstant.SpConstant.USER_INFO)
    private var mLoadingDialog: Dialog? = null
    private var mLayoutEmptyLoading: RelativeLayout? = null
    private var mLayoutLlEmptyData: LinearLayout? = null
    private var mLayoutLlLoading: LinearLayout? = null
    private var mLayoutIvLoading: ImageView? = null
    private var mLayoutLlError: LinearLayout? = null
    private var mBaseToolbar: Toolbar? = null
    private var mActivityCacheManager: ActivityCacheManager? = null
    private var mSwipeRl: SwipeRefreshLayout? = null
    protected var mBaseToolBarHelper: BaseToolBarHelper? = null
    private var mCompositeDisposable: CompositeDisposable? = null
    protected var mPermissions: Array<String>? = null

    /**
     * 获取布局ID
     */
    protected abstract fun getContentViewLayoutID(): Int

    /**
     * 设置需要的presenter
     */
    protected abstract fun initPresenter()

    /**
     * 初始化布局以及View控件
     */
    protected abstract fun initView()

    /**
     * 初始化监听事件
     */
    protected abstract fun initListener()

    /**
     * 初始化跳转数据
     */
    protected abstract fun onInitParams(bundle: Bundle)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (mActivityCacheManager == null) {
            mActivityCacheManager = ActivityCacheManager.getInstance()
        }
        mActivityCacheManager!!.addActivity(this)
        if (mLoadingDialog == null) {
            mLoadingDialog = CustomLoadingDialog.getInstance().createLoadingDialog(this, AppConstant.DialogConstant.LOADING)
        }
        EventBus.getDefault().register(this)
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID())
            if (intent != null && intent.extras != null) {
                onInitParams(intent.extras)
            } else {
                onInitParams(Bundle())
            }
            //是否要检查权限
            if (isCheckPermission()) {
                PermissionsUtils.getInstance().checkAndRequestPermissions(this, mPermissions!!, permissionsResult)
            } else {
                initPresenter()
                initView()
                initListener()
            }
        }
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        mLayoutEmptyLoading = findViewById<RelativeLayout>(R.id.layout_empty_loading)
        mLayoutLlEmptyData = findViewById<LinearLayout>(R.id.layout_ll_empty_data)
        mLayoutLlLoading = findViewById<LinearLayout>(R.id.layout_ll_loading)
        mLayoutIvLoading = findViewById<ImageView>(R.id.layout_iv_loading)
        mLayoutLlError = findViewById<LinearLayout>(R.id.layout_ll_error)
        mBaseToolbar = findViewById<Toolbar>(R.id.base_toolbar)
        mSwipeRl = findViewById<SwipeRefreshLayout>(R.id.mSwipeRl)
        if (mBaseToolbar != null) {
            setSupportActionBar(mBaseToolbar)
            mBaseToolBarHelper = BaseToolBarHelper(mBaseToolbar!!)
        }
        if (mSwipeRl != null) {
            mSwipeRl!!.setColorSchemeResources(R.color.colorAccent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearSubscribe()
        mActivityCacheManager!!.removeActivity(this)
        EventBus.getDefault().unregister(this)
    }

    /**
     * eventbus相应事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventbusAListener(o: Any) {
        if (o is ExitAppEvent) {
            clearSubscribe()
            GankIoApplation.offNetworkReceiver()
            ActivityCacheManager.getInstance().appExit(this)
        } else if (o is ApiExceptionEvent) {
            //统一处理后台返回的错误码

        } else if (o is ServerExceptionEvent) {
            ToastUtils.showToast(o.msg)
        }
    }

    /**
     * 统一管理Disposable 添加和清空订阅
     */
    protected fun addSubscribe(disposable: Disposable?) {
        if (disposable == null) return
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable)
    }

    protected fun removeSubscribe(disposable: Disposable?) {
        if (disposable == null) return
        if (mCompositeDisposable != null) {
            mCompositeDisposable!!.remove(disposable)
        }
    }

    protected fun clearSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable!!.clear()
            mCompositeDisposable = null
        }
    }

    /**
     * 检查权限及申请
     * 通过 mPermissions 设置要申请的权限
     */
    protected abstract fun checkPermission(): Boolean

    protected fun isCheckPermission(): Boolean {
        return checkPermission()
    }

    //监听权限的回调
    var permissionsResult: PermissionsUtils.IPermissionsResult = object : PermissionsUtils.IPermissionsResult {
        override fun passPermissions() {
            //权限通过
            initPresenter()
            initView()
            initListener()
        }

        override fun forbidPermissions() {
            //权限不通过
            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults)
    }

    /**
     * 基础dialog及view的统一管理
     */
    override fun showLoadingDialog() {
        mLoadingDialog!!.show()
    }

    override fun dismissLoadingDialog() {
        mLoadingDialog!!.dismiss()
    }

    override fun showLoadingView() {
        if (mLayoutEmptyLoading != null) {
            Glide.with(this).load(R.drawable.gif_loading).into(mLayoutIvLoading!!)
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

    override fun showSwipeRl() {
        if (!mSwipeRl!!.isRefreshing) {
            mSwipeRl!!.isRefreshing = true
        }
    }

    override fun dismissSwipeRl() {
        if (mSwipeRl!!.isRefreshing) {
            mSwipeRl!!.isRefreshing = false
        }
    }

}