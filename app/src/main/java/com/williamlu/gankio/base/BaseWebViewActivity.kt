package com.williamlu.gankio.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.*
import com.williamlu.gankio.R
import kotlinx.android.synthetic.main.activity_base_web_view.*

class BaseWebViewActivity : GankIoBaseActivity() {
    private var mTitle: String? = null
    private var mUrl: String? = null

    companion object {
        fun showClass(context: Context, title: String, url: String) {
            val intent = Intent(context, BaseWebViewActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("url", url)
            context.startActivity(intent)
        }
    }

    override fun onInitParams(bundle: Bundle?) {
        super.onInitParams(bundle)
        mTitle = bundle!!.getString("title")
        mUrl = bundle.getString("url")
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_base_web_view
    }

    override fun initPresenter() {
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        mBaseToolBarHelper!!.setTitleName(mTitle!!).showLeftView().setBgImg(R.drawable.lib_ic_back)

        showLoadingView()
        base_webview.loadUrl(mUrl)
        val settings = base_webview.settings
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.setSupportMultipleWindows(true)
        //设置缓存 模式  不缓存
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        //开启 Application Caches 功能
        settings.setAppCacheEnabled(false)

        base_webview.webViewClient = object :WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                dismissAllView()
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                showErrorView()
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                showLoadingView()
            }

        }
    }

    override fun initListener() {
        mBaseToolBarHelper!!.getLeftView().setOnClickListener {
            if (base_webview.canGoBack()) {
                base_webview.goBack()
            } else {
                finish()
            }
        }

        mLayoutEmptyLoading!!.setOnClickListener {
            if (mLayoutLlLoading!!.isShown) {
                return@setOnClickListener
            }
            initView()
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && base_webview.canGoBack()) {
            base_webview.goBack()//返回上个页面
            return true
        }
        return super.onKeyDown(keyCode, event)//退出H5界面
    }

}
