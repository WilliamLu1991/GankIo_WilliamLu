package com.williamlu.gankio.base

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.williamlu.datalib.bean.ApiExceptionEvent
import com.williamlu.datalib.bean.ServerExceptionEvent
import com.williamlu.gankio.AppConstant
import com.williamlu.gankio.R
import com.williamlu.toolslib.SpUtils
import com.williamlu.toolslib.ToastUtils
import com.williamlu.widgetlib.dialog.BaseToolBarHelper
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
abstract class BaseFragment : Fragment(), BaseLoadView {
    private val mSpUtils = SpUtils.getInstance(AppConstant.SpConstant.USER_INFO)
    private var rootView: View? = null
    private var mLoadingDialog: Dialog? = null
    private var mLayoutEmptyLoading: RelativeLayout? = null
    private var mLayoutLlEmptyData: LinearLayout? = null
    private var mLayoutLlLoading: LinearLayout? = null
    private var mLayoutIvLoading: ImageView? = null
    private var mLayoutLlError: LinearLayout? = null
    private var mBaseToolbar: Toolbar? = null
    protected var mBaseToolBarHelper: BaseToolBarHelper? = null
    private var mSwipeRl: SwipeRefreshLayout? = null
    private var mCompositeDisposable: CompositeDisposable? = null
    protected var mActivity: BaseActivity? = null
    private var isFirst = true

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
    protected abstract fun initView(rootView: View?)

    /**
     * 初始化监听事件
     */
    protected abstract fun initListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as BaseActivity?
        EventBus.getDefault().register(this)
        isFirst = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            if (getContentViewLayoutID() != 0) {
                rootView = inflater.inflate(getContentViewLayoutID(), container, false)
            }
        } else {
            isFirst = false
        }
        val parent = rootView!!.parent as ViewGroup?
        parent?.removeView(rootView)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isFirst) {
            setContentView(rootView)
            initPresenter()
            initView(rootView!!)
            initListener()
        }
    }

    fun setContentView(v: View?) {
        mLayoutEmptyLoading = v!!.findViewById<RelativeLayout>(R.id.layout_empty_loading)
        mLayoutLlEmptyData = v.findViewById<LinearLayout>(R.id.layout_ll_empty_data)
        mLayoutLlLoading = v.findViewById<LinearLayout>(R.id.layout_ll_loading)
        mLayoutIvLoading = v.findViewById<ImageView>(R.id.layout_iv_loading)
        mLayoutLlError = v.findViewById<LinearLayout>(R.id.layout_ll_error)
        mBaseToolbar = v.findViewById<Toolbar>(R.id.base_toolbar)
        if (mBaseToolbar != null) {
            mBaseToolBarHelper = BaseToolBarHelper(mBaseToolbar!!)
        }
        mSwipeRl = v.findViewById<SwipeRefreshLayout>(R.id.mSwipeRl)
        if (mSwipeRl != null) {
            mSwipeRl!!.setColorSchemeResources(R.color.colorAccent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearSubscribe()
        EventBus.getDefault().unregister(this)
    }

    /**
     * eventbus相应事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventbusBaseFListener(o: Any) {
        if (o is ApiExceptionEvent) {
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
     * 基础dialog及view的统一管理
     */
    override fun showLoadingDialog() {
        mActivity!!.showLoadingDialog()
    }

    override fun dismissLoadingDialog() {
        mActivity!!.dismissLoadingDialog()
    }

    override fun showLoadingView() {
        if (mLayoutEmptyLoading != null) {
            Glide.with(this).load(R.drawable.lib_gif_loading).into(mLayoutIvLoading!!)
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