package com.williamlu.datalib.base

import com.google.gson.JsonSyntaxException
import com.orhanobut.logger.Logger
import com.williamlu.datalib.DataConstant
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/*
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 
 */
abstract class ApiObserver<T> : Observer<T> {
    //(private val mContext: Context, dialogMsg: String)
    //private var mDialog: Dialog? = null
    private var mDisposable: Disposable? = null

    /*init {
        if (!TextUtils.isEmpty(dialogMsg)) {
            mDialog = CustomLoadingDialog.createLoadingDialog(mContext, dialogMsg)
            mDialog!!.show()
        }
    }*/

    override fun onSubscribe(d: Disposable) {
        mDisposable = d
    }

    override fun onError(e: Throwable) {
        /*if (mDialog != null && mDialog!!.isShowing) {
            mDialog!!.dismiss()
        }*/
        var showMsg = ""
        if (e is ApiException) {
            //处理服务器返回的错误
            showMsg = e.message.toString()
        } else if (e is ConnectException || e is UnknownHostException) {
            showMsg = DataConstant.ToastConstant.ERROR_NETWORK
        } else if (e is TimeoutException || e is SocketTimeoutException) {
            showMsg = DataConstant.ToastConstant.ERROR_TIMEOUT
        } else if (e is JsonSyntaxException) {
            showMsg = DataConstant.ToastConstant.ERROR_JSONSYNTAX
        } else if (e is HttpException) {
            if (e.code() == 400) {
                showMsg = DataConstant.ToastConstant.ERROR_URL_OR_PARAMETER
            }
            if (e.code() == 401) {
                showMsg = DataConstant.ToastConstant.ERROR_NO_AUTHORIZE
            }
            if (e.code() == 403) {
                showMsg = DataConstant.ToastConstant.ERROR_NO_ACCESS
            }
            if (e.code() == 404) {
                showMsg = DataConstant.ToastConstant.ERROR_RESOURCE_NO_EXIST
            }
            if (e.code() == 500) {
                showMsg = DataConstant.ToastConstant.ERROR_INSIDE
            }
        } else {
            showMsg = DataConstant.ToastConstant.ERROR_SERVER
        }
        //ToastUtils.showToast(showMsg)
        Logger.e(e.toString() + "," + showMsg)

        if (mDisposable != null && !mDisposable!!.isDisposed) {
            mDisposable!!.dispose()
        }

    }

    override fun onComplete() {
        /*if (mDialog != null && mDialog!!.isShowing) {
            mDialog!!.dismiss()
        }*/
        if (mDisposable != null && !mDisposable!!.isDisposed) {
            mDisposable!!.dispose()
        }
    }
}