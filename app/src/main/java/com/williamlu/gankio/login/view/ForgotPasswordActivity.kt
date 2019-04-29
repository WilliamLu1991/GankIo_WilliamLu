package com.williamlu.gankio.login.view

import android.text.TextUtils
import com.williamlu.gankio.AppConstant
import com.williamlu.gankio.R
import com.williamlu.gankio.base.GankIoBaseActivity
import com.williamlu.gankio.event.LoginOutEvent
import com.williamlu.toolslib.RxCountDownUtils
import com.williamlu.toolslib.SpUtils
import com.williamlu.toolslib.ToastUtils
import com.williamlu.toolslib.VerificationUtil
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_forgot_password.*
import org.greenrobot.eventbus.EventBus

/**
 * @Author: WilliamLu
 * @Date: 2018/12/27
 * @Description:找回密码页
 */
class ForgotPasswordActivity : GankIoBaseActivity() {

    private val mSpUtils = SpUtils.getInstance(AppConstant.SpConstant.USER_INFO)
    private var mPhone: String? = null
    private var mSecurityCode: String? = null
    private var mToken: String? = null
    private var SECURITY_CODE_LENGTH: Int = 6
    private var PHONE_LENGTH: Int = 11
    private var PASSWORD_MIN_LENGTH: Int = 6

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_forgot_password
    }

    override fun initPresenter() {
    }

    override fun initView() {
        mBaseToolBarHelper!!.setTitleName("找回密码").showLeftView().setBgImg(R.drawable.lib_ic_back)
    }

    override fun initListener() {
        mBaseToolBarHelper!!.getLeftView().setOnClickListener {
            finish()
        }

        //获取验证码
        forgotpwd_bt_getcode.setOnClickListener {
            mPhone = forgotpwd_et_phone.text.toString().trim()
            if (TextUtils.isEmpty(mPhone)) {
                ToastUtils.showToast(AppConstant.ToastConstant.NO_INPUT_PHONE)
            } else if (VerificationUtil.isValidTelNumber(mPhone!!)) {
                RxCountDownUtils().countdown(30, object : RxCountDownUtils.onRxCountDownListener {
                    override fun onSubscribe(disposable: Disposable) {
                        addSubscribe(disposable)
                    }

                    override fun onBefore() {
                        ToastUtils.showToast(AppConstant.ToastConstant.SUCCESS_SEND)
                        forgotpwd_bt_getcode.isClickable = false
                        forgotpwd_bt_getcode.setBackgroundResource(R.drawable.shape_bg_ffffff_s_cccccc_r4)
                        forgotpwd_bt_getcode.setTextColor(resources.getColor(R.color.color_cccccc))
                    }

                    override fun onNext(time: Int) {
                        forgotpwd_bt_getcode.text = time.toString() + " s"
                    }

                    override fun onComplete() {
                        forgotpwd_bt_getcode.text = "获取验证码"
                        forgotpwd_bt_getcode.isClickable = true
                        forgotpwd_bt_getcode.setTextColor(resources.getColor(R.color.color_ff2929))
                        forgotpwd_bt_getcode.setBackgroundResource(R.drawable.shape_bg_ffffff_s_ff2929_r4)
                    }
                })
            } else {
                ToastUtils.showToast(AppConstant.ToastConstant.ERROR_INPUT_PHONE)
            }
        }

        //提交
        forgotpwd_bt_go.setOnClickListener {

            mPhone = forgotpwd_et_phone.text.toString().trim()

            if (TextUtils.isEmpty(mPhone)) {
                ToastUtils.showToast(AppConstant.ToastConstant.NO_INPUT_PHONE)
                return@setOnClickListener
            } else if (!VerificationUtil.isValidTelNumber(mPhone!!)) {
                ToastUtils.showToast(AppConstant.ToastConstant.ERROR_INPUT_PHONE)
                return@setOnClickListener
            }

            mSecurityCode = forgotpwd_et_code.text.toString().trim()
            if (TextUtils.isEmpty(mSecurityCode)) {
                ToastUtils.showToast(AppConstant.ToastConstant.NO_INPUT_SECCODE)
                return@setOnClickListener
            }

            val newPassword = forgotpwd_et_new_pwd.text.toString().trim()
            val confirmNewPassword = forgotpwd_et_confirm_pwd.text.toString().trim()
            if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmNewPassword)) {
                ToastUtils.showToast(AppConstant.ToastConstant.NO_INPUT_NEW_PWD)
                return@setOnClickListener
            } else if (newPassword != confirmNewPassword) {
                ToastUtils.showToast(AppConstant.ToastConstant.ERROR_INPUT_TWO_PWD)
                return@setOnClickListener
            } else if (newPassword!!.length < PASSWORD_MIN_LENGTH) {
                ToastUtils.showToast(AppConstant.ToastConstant.ERROR_INPUT_PWD)
                return@setOnClickListener
            }

            setPwdSuccess()
        }
    }

    private fun setPwdSuccess() {
        ToastUtils.showToast(AppConstant.ToastConstant.SUCCESS_SET_PWD)
        mSpUtils.put(AppConstant.SpConstant.USER_PASSWORD, "")
        mSpUtils.put(AppConstant.SpConstant.USER_IS_LOGIN, false)
        EventBus.getDefault().post(LoginOutEvent(this))
        finish()
    }

}
