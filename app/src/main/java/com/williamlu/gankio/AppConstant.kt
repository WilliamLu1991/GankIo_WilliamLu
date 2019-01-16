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
        const val URL_BASE_PRODUCT = "https://gank.io/api/"
        const val URL_BASE_TEST = "https://gank.io/api/"
    }

    object ConfigConstant {
        const val BUGLY_APPID = "c7a1462f22"
        const val TINKER_NAME = "com.williamlu.gankio.MyTinkerApplationLike"
        const val UMENG_APPKEY = "5c1ba8a0f1f556ace1000513"
    }

    object ToastConstant {
        const val EXIT_APP = "再次点击将退出应用"
        const val NO_INPUT_PHONE = "您还未填写手机号码哦～"
        const val ERROR_INPUT_PHONE = "您填写的手机号码不准确哦~"
        const val NO_INPUT_SECCODE = "您还未填写验证码哦～"
        const val NO_INPUT_PWD = "您还未填写密码哦～"
        const val ERROR_INPUT_PWD = "请填写6-16位的密码哦~"
        const val SUCCESS_LOGIN = "登录成功"
        const val SUCCESS_SEND = "发送成功"
        const val SUCCESS_GET = "获取成功"
        const val SUCCESS_ALTER = "修改成功"
        const val SUCCESS_REGISTER = "注册成功"
        const val SUCCESS_SET_PWD = "设置成功"
        const val SUCCESS_CONFIRM = "验证成功"
        const val SUCCESS_CHANGE = "更换成功"
        const val SUCCESS_PUT = "提交成功"
        const val SUCCESS_SAVE = "保存成功"
        const val NO_INPUT_NEW_PWD = "您还未填写新密码哦～"
        const val NO_INPUT_SUGGEST = "您还未填写任何意见哦～"
        const val NO_EMPTY = "输入地址不能为空哦~"
        const val ERROR_REQUEST = "请求失败"
        const val ERROR_INPUT_TWO_PWD = "您两次输入的密码不一致哦~"
        const val ERROR_UPLOAD = "上传失败"
        const val ERROR_SERVER_TOKEN = "登录失效，请重新登录"
        const val SUCCESS_BIND = "绑定成功"
        const val ALREADY_BIND_WEIXIN = "您已绑定过微信"
        const val ALREADY_BIND_QQ = "您已绑定过QQ"
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