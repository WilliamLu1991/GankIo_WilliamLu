package com.williamlu.gankio.splash.view

import android.Manifest
import android.content.Intent
import com.williamlu.gankio.AppConstant
import com.williamlu.gankio.R
import com.williamlu.gankio.base.GankIoBaseActivity
import com.williamlu.gankio.main.view.MainActivity
import com.williamlu.toolslib.RxCountDownUtils
import com.williamlu.toolslib.SpUtils
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : GankIoBaseActivity() {
    private val mSpUtils = SpUtils.getInstance(AppConstant.SpConstant.USER_INFO)

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_splash
    }

    override fun initPresenter() {

    }

    override fun checkPermission(): Boolean {
        mPermissions = arrayOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.KILL_BACKGROUND_PROCESSES,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.BLUETOOTH
                //Manifest.permission.SYSTEM_ALERT_WINDOW
                //Manifest.permission.WRITE_APN_SETTINGS,
                //Manifest.permission.SET_DEBUG_APP,
                //Manifest.permission.READ_LOGS
        )
        return true
    }

    override fun initView() {
        RxCountDownUtils().countdown(3, object : RxCountDownUtils.onRxCountDownListener {
            override fun onSubscribe(disposable: Disposable) {
                addSubscribe(disposable)
            }

            override fun onBefore() {

            }

            override fun onNext(time: Int) {
                splash_tv_time.text = "跳过：" + time.toString() + " s"
            }

            override fun onComplete() {
                goActivity()
            }

        })
    }

    override fun initListener() {
        splash_tv_time.setOnClickListener {
            goActivity()
        }
    }

    private fun goActivity() {
        val isFirstStartApp = mSpUtils.getBoolean(AppConstant.SpConstant.USER_IS_FIRST_START_APP, true)
        if (isFirstStartApp) {
            startActivity(Intent(this@SplashActivity, GuideActivity::class.java))
        } else {
            startActivity(Intent(baseContext, MainActivity::class.java))
        }
        finish()
    }

}
