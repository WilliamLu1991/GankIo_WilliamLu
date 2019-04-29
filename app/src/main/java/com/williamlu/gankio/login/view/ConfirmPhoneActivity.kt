package com.williamlu.gankio.login.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
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
import kotlinx.android.synthetic.main.activity_confirm_phone.*
import org.greenrobot.eventbus.EventBus

/**
 * @Author: WilliamLu
 * @Data: 2018/12/28
 * @Description:验证手机号
 */
class ConfirmPhoneActivity : GankIoBaseActivity() {

    private val mSpUtils = SpUtils.getInstance(AppConstant.SpConstant.USER_INFO)
    private var mPhone: String? = null
    private var mSecurityCode: String? = null
    private var mToken: String? = null
    private val SECURITY_CODE_LENGTH: Int = 6
    private val PHONE_LENGTH: Int = 11
    private var mWechatOpenID: String? = null
    private var mQQOpenID: String? = null

    companion object {
        fun showClass(context: Context, wechatopenid: String, qqopenid: String) {
            val intent = Intent(context, ConfirmPhoneActivity::class.java)
            intent.putExtra("wechatopenid", wechatopenid)
            intent.putExtra("qqopenid", qqopenid)
            context.startActivity(intent)
        }
    }

    override fun onInitParams(bundle: Bundle?) {
        super.onInitParams(bundle)
        mWechatOpenID = bundle!!.getString("wechatopenid", "")
        mQQOpenID = bundle.getString("qqopenid", "")
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_confirm_phone
    }

    override fun initPresenter() {
    }

    override fun initView() {
        mBaseToolBarHelper!!.setTitleName("验证手机号").showLeftView().setBgImg(R.drawable.lib_ic_back)
    }

    override fun initListener() {
        mBaseToolBarHelper!!.getLeftView().setOnClickListener {
            finish()
        }

        //获取验证码
        confirmphone_bt_getcode.setOnClickListener {
            mPhone = confirmphone_et_phone.text.toString().trim()
            if (TextUtils.isEmpty(mPhone)) {
                ToastUtils.showToast(AppConstant.ToastConstant.NO_INPUT_PHONE)
            } else if (VerificationUtil.isValidTelNumber(mPhone!!)) {

                RxCountDownUtils().countdown(30, object : RxCountDownUtils.onRxCountDownListener {
                    override fun onSubscribe(disposable: Disposable) {
                        addSubscribe(disposable)
                    }

                    override fun onBefore() {
                        ToastUtils.showToast(AppConstant.ToastConstant.SUCCESS_SEND)
                        confirmphone_bt_getcode.isClickable = false
                        confirmphone_bt_getcode.setBackgroundResource(R.drawable.shape_bg_ffffff_s_cccccc_r4)
                        confirmphone_bt_getcode.setTextColor(resources.getColor(R.color.color_cccccc))
                    }

                    override fun onNext(time: Int) {
                        confirmphone_bt_getcode.text = time.toString() + " s"
                    }

                    override fun onComplete() {
                        confirmphone_bt_getcode.text = "获取验证码"
                        confirmphone_bt_getcode.isClickable = true
                        confirmphone_bt_getcode.setTextColor(resources.getColor(R.color.color_ff2929))
                        confirmphone_bt_getcode.setBackgroundResource(R.drawable.shape_bg_ffffff_s_ff2929_r4)
                    }
                })
            } else {
                ToastUtils.showToast(AppConstant.ToastConstant.ERROR_INPUT_PHONE)
            }
        }

        //确定
        confirmphone_bt_go.setOnClickListener {

            mPhone = confirmphone_et_phone.text.toString().trim()

            if (TextUtils.isEmpty(mPhone)) {
                ToastUtils.showToast(AppConstant.ToastConstant.NO_INPUT_PHONE)
                return@setOnClickListener
            } else if (!VerificationUtil.isValidTelNumber(mPhone!!)) {
                ToastUtils.showToast(AppConstant.ToastConstant.ERROR_INPUT_PHONE)
                return@setOnClickListener
            }

            mSecurityCode = confirmphone_et_code.text.toString().trim()
            if (TextUtils.isEmpty(mSecurityCode)) {
                ToastUtils.showToast(AppConstant.ToastConstant.NO_INPUT_SECCODE)
                return@setOnClickListener
            }

            confirmSuccess()
        }
    }

    private fun confirmSuccess() {
        startActivity(Intent(this, MainActivity::class.java))
        if (!TextUtils.isEmpty(mPhone)) {
            mSpUtils.put(AppConstant.SpConstant.USER_PHONE, mPhone!!)
        }
        mSpUtils.put(AppConstant.SpConstant.USER_IS_LOGIN, true)
        ToastUtils.showToast(AppConstant.ToastConstant.SUCCESS_CONFIRM)
        EventBus.getDefault().post(LoginSuccessEvent(this))
        finish()
    }

}
