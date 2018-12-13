package com.williamlu.gankio.splash

import android.content.Intent
import android.os.Bundle
import com.williamlu.gankio.R
import com.williamlu.gankio.base.GankBaseActivity
import com.williamlu.gankio.home.view.MainActivity
import com.williamlu.toolslib.KeepAliveUtils
import com.williamlu.toolslib.RxCountDownUtils
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : GankBaseActivity() {
    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_splash
    }

    override fun initPresenter() {

    }

    override fun initView(savedInstanceState: Bundle?) {

        RxCountDownUtils.getInstance().countdown(5, object : RxCountDownUtils.onRxCountDownListener {
            override fun onSubscribe(disposable: Disposable) {
                addSubscribe(disposable)
            }

            override fun onBefore() {

            }

            override fun onNext(time: Int) {
                splash_tv_time.text = "跳过：" + time.toString() + " s"
            }

            override fun onComplete() {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }

        })

        KeepAliveUtils.getInstance().startKeepAlive(3, object : KeepAliveUtils.onKeepAliveListener {
            override fun onSubscribe(disposable: Disposable) {
                addSubscribe(disposable)
            }

            override fun onNext(t: Long) {

            }
        })
    }

    override fun initListener() {
        splash_tv_time.setOnClickListener {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }

}