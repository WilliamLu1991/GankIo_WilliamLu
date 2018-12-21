package com.williamlu.gankio

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
class AppConstant {
    companion object {
        fun getBaseUrl(): String {
            return if (BuildConfig.DEBUG) {
                UrlConstant.URL_BASE_TEST
            } else {
                UrlConstant.URL_BASE_PRODUCT
            }
        }
    }

    object UrlConstant {
        const val URL_BASE_PRODUCT = "https://api.douban.com/v2/"
        const val URL_BASE_TEST = "https://api.douban.com/v2/"
    }

    object ConfigConstant {
        const val BUGLY_APPID = "c7a1462f22"
        const val TINKER_NAME = "com.williamlu.gankio.MyTinkerApplationLike"
        const val UMENG_APPKEY = "5c1ba8a0f1f556ace1000513"
    }

    object ToastConstant {
        const val EXIT_APP = "再次点击将退出应用！"
        const val NO_INPUT_PHONE = "您还未填写手机号码哦～！"
        const val ERROR_INPUT_PHONE = "您填写的手机号码不准确哦！"
        const val NO_INPUT_SECCODE = "您还未填写验证码哦～！"
        const val SUCCESS_LOGIN = "登录成功！"
        const val SUCCESS_SEND = "发送成功！"
        const val NO_EMPTY = "输入地址不能为空哦~！"
    }

    object DialogConstant {
        const val LOGIN_LOADING = "登录中..."
        const val LOADING = "加载中..."
    }

    object SpConstant {
        const val USERNAME_HISTORY = "username_history"
        const val USER_INFO = "user_info"
        const val USER_TOKEN = "user_token"
        const val USER_NAME = "user_name"
    }

}