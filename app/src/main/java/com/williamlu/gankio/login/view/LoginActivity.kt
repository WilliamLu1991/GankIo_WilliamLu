package com.williamlu.gankio.login.view

import android.content.Intent
import android.support.design.widget.TabLayout
import android.text.TextUtils
import android.view.View
import com.orhanobut.logger.Logger
import com.umeng.socialize.UMAuthListener
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import com.williamlu.gankio.AppConstant
import com.williamlu.gankio.R
import com.williamlu.gankio.base.GankIoBaseActivity
import com.williamlu.gankio.event.LoginSuccessEvent
import com.williamlu.gankio.main.view.MainActivity
import com.williamlu.toolslib.RxCountDownUtils
import com.williamlu.toolslib.SpUtils
import com.williamlu.toolslib.ToastUtils
import com.williamlu.toolslib.VerificationUtil
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus

/**
 * @Author: WilliamLu
 * @Date: 2018/12/26
 * @Description:登录页
 */
class LoginActivity : GankIoBaseActivity() {

    private val mSpUtils = SpUtils.getInstance(AppConstant.SpConstant.USER_INFO)
    private var mPhone: String? = null
    private var mSecurityCode: String? = null
    private var mPassword: String? = null
    private var mToken: String? = null
    private var mWechatOpenID: String = ""
    private var mQQOpenID: String = ""
    private var isQuickLogin: Boolean = true
    private var SECURITY_CODE_LENGTH: Int = 6
    private var PHONE_LENGTH: Int = 11
    private var PASSWORD_MIN_LENGTH: Int = 6

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_login
    }

    override fun initPresenter() {
    }

    override fun initView() {
        mBaseToolBarHelper!!.setTitleName("登录GankIo").showLeftView().setBgImg(R.drawable.lib_ic_back)
        val phoneHistory = mSpUtils.getString(AppConstant.SpConstant.USER_PHONE)
        if (!TextUtils.isEmpty(phoneHistory)) {
            login_et_phone.setText(phoneHistory)
        }
        val pwdHistory = mSpUtils.getString(AppConstant.SpConstant.USER_PASSWORD)
        if (!TextUtils.isEmpty(pwdHistory)) {
            login_et_pwd.setText(pwdHistory)
        }
    }

    override fun initListener() {
        mBaseToolBarHelper!!.getLeftView().setOnClickListener {
            finish()
        }

        //快捷登录及账号密码登录tab点击回调
        login_tablayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.text.toString().equals(getString(R.string.login_quick))) {
                    //快捷登录
                    loginQuick()
                } else if (tab!!.text.toString().equals(getString(R.string.login_account_pwd))) {
                    //账号密码登录
                    loginAccountPwd()
                }
            }
        })

        //获取验证码
        login_bt_getcode.setOnClickListener {
            mPhone = login_et_phone.text.toString().trim()

            if (TextUtils.isEmpty(mPhone)) {
                ToastUtils.showToast(AppConstant.ToastConstant.NO_INPUT_PHONE)
            } else if (VerificationUtil.isValidTelNumber(mPhone!!)) {

                RxCountDownUtils().countdown(30, object : RxCountDownUtils.onRxCountDownListener {
                    override fun onSubscribe(disposable: Disposable) {
                        addSubscribe(disposable)
                    }

                    override fun onBefore() {
                        login_bt_getcode.isClickable = false
                        login_bt_getcode.setBackgroundResource(R.drawable.shape_bg_ffffff_s_cccccc_r4)
                        login_bt_getcode.setTextColor(resources.getColor(R.color.color_cccccc))
                    }

                    override fun onNext(time: Int) {
                        login_bt_getcode.text = time.toString() + " s"
                    }

                    override fun onComplete() {
                        login_bt_getcode.text = "获取验证码"
                        login_bt_getcode.isClickable = true
                        login_bt_getcode.setTextColor(resources.getColor(R.color.color_ff2929))
                        login_bt_getcode.setBackgroundResource(R.drawable.shape_bg_ffffff_s_ff2929_r4)
                    }
                })
            } else {
                ToastUtils.showToast(AppConstant.ToastConstant.ERROR_INPUT_PHONE)
            }
        }

        //登录
        login_bt_go.setOnClickListener {

            mPhone = login_et_phone.text.toString().trim()

            if (TextUtils.isEmpty(mPhone)) {
                ToastUtils.showToast(AppConstant.ToastConstant.NO_INPUT_PHONE)
                return@setOnClickListener
            } else if (!VerificationUtil.isValidTelNumber(mPhone!!)) {
                ToastUtils.showToast(AppConstant.ToastConstant.ERROR_INPUT_PHONE)
                return@setOnClickListener
            }

            if (isQuickLogin) {
                mSecurityCode = login_et_code.text.toString().trim()
                if (TextUtils.isEmpty(mSecurityCode)) {
                    ToastUtils.showToast(AppConstant.ToastConstant.NO_INPUT_SECCODE)
                    return@setOnClickListener
                }
                loginSuccess()
            } else {
                mPassword = login_et_pwd.text.toString().trim()
                if (TextUtils.isEmpty(mPassword)) {
                    ToastUtils.showToast(AppConstant.ToastConstant.NO_INPUT_PWD)
                    return@setOnClickListener
                } else if (mPassword!!.length < PASSWORD_MIN_LENGTH) {
                    ToastUtils.showToast(AppConstant.ToastConstant.ERROR_INPUT_PWD)
                    return@setOnClickListener
                }
                loginSuccess()
            }
        }

        //setIsClickLogin()

        //新用户注册
        login_tv_user_register.setOnClickListener {
            startActivity(Intent(this, UserRegisterActivity::class.java))
        }

        //找回密码
        login_tv_forgot_pwd.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        //微信登录
        login_iv_weixin.setOnClickListener {
            showLoadingDialog()
            UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, object : UMAuthListener {
                override fun onComplete(p0: SHARE_MEDIA?, p1: Int, p2: MutableMap<String, String>?) {
                    Logger.d("unionid: " + p2!!.get("uid"))
                    Logger.d("昵称: " + p2.get("name"))
                    Logger.d("头像: " + p2.get("iconurl"))
                    Logger.d("性别: " + p2.get("gender"))

                    mWechatOpenID = p2.get("uid")!!
                    loginSuccess()
                }

                override fun onCancel(p0: SHARE_MEDIA?, p1: Int) {
                    dismissLoadingDialog()
                }

                override fun onError(p0: SHARE_MEDIA?, p1: Int, p2: Throwable?) {
                    dismissLoadingDialog()
                    ToastUtils.showToast(p2!!.message!!)
                    Logger.e(p0.toString() + "----" + p1.toString() + "----" + p2.toString())
                }

                override fun onStart(p0: SHARE_MEDIA?) {
                }

            })
        }

        //QQ登录
        login_iv_qq.setOnClickListener {
            showLoadingDialog()
            UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ, object : UMAuthListener {
                override fun onComplete(p0: SHARE_MEDIA?, p1: Int, p2: MutableMap<String, String>?) {
                    Logger.d("openid: " + p2!!.get("uid"))
                    Logger.d("昵称: " + p2!!.get("name"))
                    Logger.d("头像: " + p2!!.get("iconurl"))
                    Logger.d("性别: " + p2!!.get("gender"))

                    mQQOpenID = p2!!.get("uid")!!
                    loginSuccess()
                }

                override fun onCancel(p0: SHARE_MEDIA?, p1: Int) {
                    dismissLoadingDialog()
                }

                override fun onError(p0: SHARE_MEDIA?, p1: Int, p2: Throwable?) {
                    dismissLoadingDialog()
                    ToastUtils.showToast(p2!!.message!!)
                    Logger.e(p0.toString() + "----" + p1.toString() + "----" + p2.toString())
                }

                override fun onStart(p0: SHARE_MEDIA?) {
                }

            })
        }
    }

    private fun loginSuccess() {
        startActivity(Intent(this, MainActivity::class.java))

        if (!TextUtils.isEmpty(mPhone)) {
            mSpUtils.put(AppConstant.SpConstant.USER_PHONE, mPhone!!)
        }
        if (!TextUtils.isEmpty(mPassword)) {
            mSpUtils.put(AppConstant.SpConstant.USER_PASSWORD, mPassword!!)
        }
        mSpUtils.put(AppConstant.SpConstant.USER_IS_LOGIN, true)
        ToastUtils.showToast(AppConstant.ToastConstant.SUCCESS_LOGIN)
        EventBus.getDefault().post(LoginSuccessEvent(this))
        finish()
    }

    private fun loginQuick() {
        isQuickLogin = true
        login_ll_phone_code.visibility = View.VISIBLE
        login_et_pwd.visibility = View.GONE
        login_tv_forgot_pwd.visibility = View.GONE
    }

    private fun loginAccountPwd() {
        isQuickLogin = false
        login_ll_phone_code.visibility = View.GONE
        login_et_pwd.visibility = View.VISIBLE
        login_tv_forgot_pwd.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }

}
