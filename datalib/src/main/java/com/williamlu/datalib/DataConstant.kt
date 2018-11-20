package com.williamlu.datalib

/*
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 
 */
class DataConstant {

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

    object ToastConstant {
        const val ERROR_NETWORK = "网络异常，请检查网络！"
        const val ERROR_TIMEOUT = "网络超时，请稍后再试！"
        const val ERROR_JSONSYNTAX = "数据解析异常！"
        const val ERROR_SERVER = "服务器异常！"
        const val ERROR_DATA = "数据异常！"
        const val ERROR_URL_OR_PARAMETER = "请求的地址不存在或者包含不支持的参数!"
        const val ERROR_NO_AUTHORIZE = "未授权!"
        const val ERROR_NO_ACCESS = "被禁止访问!"
        const val ERROR_RESOURCE_NO_EXIST = "请求的资源不存在!"
        const val ERROR_INSIDE = "内部错误！"

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