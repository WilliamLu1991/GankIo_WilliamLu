package com.williamlu.gankio

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.support.multidex.MultiDex
import com.meituan.android.walle.WalleChannelReader
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.squareup.leakcanary.LeakCanary
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.tinker.TinkerManager.getApplication
import com.williamlu.toolslib.GlobalCache
import com.williamlu.toolslib.NetworkChangedReceiver
import com.williamlu.toolslib.ToastUtils

/**
 * @Author: WilliamLu
 * @Date: 2018/11/21
 * @Description:
 */
class GankIoApplation : Application() {
    companion object {
        private var mReceiver: NetworkChangedReceiver? = null
        fun offNetworkReceiver() {
            GlobalCache.getContext().unregisterReceiver(mReceiver)
        }
    }

    override fun onCreate() {
        super.onCreate()
        initBuggly()
        initLeakCanary()
        initGlobalCache()
        initMultiDex()
        //初始化日志
        initLogger()
        //初始化网络监听
        initNetwork()
    }

    private fun initBuggly() {
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
        val channel = WalleChannelReader.getChannel(getApplication())
        Bugly.setAppChannel(getApplication(), channel)
        Bugly.init(this, AppConstant.ConfigConstant.BUGLY_APPID, BuildConfig.DEBUG)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base)
        // 安装tinker
        Beta.installTinker()
    }

    private fun initLeakCanary() {
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return
            }
            LeakCanary.install(this)
        }
    }

    private fun initLogger() {
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    private fun initGlobalCache() {
        GlobalCache.getInstance().registerContext(this)
    }

    private fun initMultiDex() {
        MultiDex.install(this)
    }

    private fun initNetwork() {
        mReceiver = NetworkChangedReceiver(object : NetworkChangedReceiver.onChangedListener {
            override fun onNetworkConnected(msg: String) {
                //ToastUtils.showToast(msg)
            }

            override fun onNetworkDisconnected(msg: String) {
                ToastUtils.showToast(msg)
            }
        })
        val filter = IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(mReceiver, filter)
    }
}