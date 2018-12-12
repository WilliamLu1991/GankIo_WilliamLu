package com.williamlu.gankio.splash

import android.content.Intent
import android.os.Bundle
import com.williamlu.gankio.R
import com.williamlu.gankio.base.GankBaseActivity
import com.williamlu.gankio.home.view.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : GankBaseActivity() {
    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_splash
    }

    override fun initPresenter() {

    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initListener() {
        splash_tv_time.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
