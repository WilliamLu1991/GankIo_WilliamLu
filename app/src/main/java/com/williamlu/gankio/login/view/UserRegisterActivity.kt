package com.williamlu.gankio.login.view

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.williamlu.gankio.AppConstant
import com.williamlu.gankio.R
import com.williamlu.gankio.base.GankIoBaseActivity
import com.williamlu.gankio.base.PDFViewerActivity
import com.williamlu.toolslib.RxCountDownUtils
import com.williamlu.toolslib.ToastUtils
import com.williamlu.toolslib.VerificationUtil
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_user_register.*

/**
 * @Author: WilliamLu
 * @Date: 2018/12/27
 * @Description:新用户注册页
 */
class UserRegisterActivity : GankIoBaseActivity() {

    private var mPhone: String? = null
    private var mSecurityCode: String? = null
    private var mToken: String? = null
    private var SECURITY_CODE_LENGTH: Int = 6
    private var PHONE_LENGTH: Int = 11
    private var PASSWORD_MIN_LENGTH: Int = 6

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_user_register
    }

    override fun initPresenter() {
    }

    override fun initView() {
        mBaseToolBarHelper!!.setTitleName("注册信用客").showLeftView().setBgImg(R.drawable.lib_ic_back)
    }

    override fun initListener() {
        mBaseToolBarHelper!!.getLeftView().setOnClickListener {
            finish()
        }

        //输入手机号
        register_et_phone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //setIsClickLogin()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        //输入验证码
        register_et_code.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //setIsClickLogin()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        //获取验证码
        register_bt_getcode.setOnClickListener {
            mPhone = register_et_phone.text.toString().trim()
            if (TextUtils.isEmpty(mPhone)) {
                ToastUtils.showToast(AppConstant.ToastConstant.NO_INPUT_PHONE)
            } else if (VerificationUtil.isValidTelNumber(mPhone!!)) {
                RxCountDownUtils().countdown(30, object : RxCountDownUtils.onRxCountDownListener {
                    override fun onSubscribe(disposable: Disposable) {
                        addSubscribe(disposable)
                    }

                    override fun onBefore() {
                        ToastUtils.showToast(AppConstant.ToastConstant.SUCCESS_SEND)
                        register_bt_getcode.isClickable = false
                        register_bt_getcode.setBackgroundResource(R.drawable.shape_bg_ffffff_s_cccccc_r4)
                        register_bt_getcode.setTextColor(resources.getColor(R.color.color_cccccc))
                    }

                    override fun onNext(time: Int) {
                        register_bt_getcode.text = time.toString() + " s"
                    }

                    override fun onComplete() {
                        register_bt_getcode.text = "获取验证码"
                        register_bt_getcode.isClickable = true
                        register_bt_getcode.setTextColor(resources.getColor(R.color.color_ff2929))
                        register_bt_getcode.setBackgroundResource(R.drawable.shape_bg_ffffff_s_ff2929_r4)
                    }
                })
            } else {
                ToastUtils.showToast(AppConstant.ToastConstant.ERROR_INPUT_PHONE)
            }
        }

        //注册
        register_bt_go.setOnClickListener {

            mPhone = register_et_phone.text.toString().trim()
            var password = register_et_pwd.text.toString().trim()
            var confirmPassword = register_et_confirm_pwd.text.toString().trim()

            if (TextUtils.isEmpty(mPhone)) {
                ToastUtils.showToast(AppConstant.ToastConstant.NO_INPUT_PHONE)
                return@setOnClickListener
            } else if (!VerificationUtil.isValidTelNumber(mPhone!!)) {
                ToastUtils.showToast(AppConstant.ToastConstant.ERROR_INPUT_PHONE)
                return@setOnClickListener
            } else if (TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                ToastUtils.showToast(AppConstant.ToastConstant.NO_INPUT_PWD)
                return@setOnClickListener
            } else if (password != confirmPassword) {
                ToastUtils.showToast(AppConstant.ToastConstant.ERROR_INPUT_TWO_PWD)
                return@setOnClickListener
            } else if (password.length < PASSWORD_MIN_LENGTH) {
                ToastUtils.showToast(AppConstant.ToastConstant.ERROR_INPUT_PWD)
                return@setOnClickListener
            }

            mSecurityCode = register_et_code.text.toString().trim()
            if (TextUtils.isEmpty(mSecurityCode)) {
                ToastUtils.showToast(AppConstant.ToastConstant.NO_INPUT_SECCODE)
                return@setOnClickListener
            }

            registerSuccess()
        }

        register_tv_agreement.setOnClickListener {
            PDFViewerActivity.showClass(this, "用户协议与隐私政策", AppConstant.ConfigConstant.PDF_USER_AGREEMENT)
        }
    }

    private fun registerSuccess() {
        ToastUtils.showToast(AppConstant.ToastConstant.SUCCESS_REGISTER)
        finish()
    }
}
