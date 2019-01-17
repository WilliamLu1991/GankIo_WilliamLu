package com.williamlu.gankio.splash.view

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

    override fun initView() {
        RxCountDownUtils().countdown(5, object : RxCountDownUtils.onRxCountDownListener {
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
