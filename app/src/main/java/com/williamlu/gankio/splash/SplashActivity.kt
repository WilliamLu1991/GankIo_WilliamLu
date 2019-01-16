package com.williamlu.gankio.splash

import android.content.Intent
import com.williamlu.gankio.R
import com.williamlu.gankio.base.GankIoBaseActivity
import com.williamlu.gankio.home.view.MainActivity
import com.williamlu.toolslib.RxCountDownUtils
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : GankIoBaseActivity() {
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
                startActivity(Intent(baseContext, MainActivity::class.java))
                finish()
            }

        })
    }

    override fun initListener() {
        splash_tv_time.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

}
