package com.williamlu.toolslib

import android.content.Context

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 
 */
class GlobalCache {
    private var mContext: Context? = null

    fun registerContext(context: Context) {
        this.mContext = context
    }

    fun setLogOpen(printLog: Boolean) {
        if (mContext == null) {
            throw NullPointerException("You shall call GlobalCache.registerContext first")
        }
    }

    companion object {
        private val instance = GlobalCache()

        fun getInstance(): GlobalCache {
            return instance
        }

        fun getContext(): Context {
            return getInstance().mContext!!
        }
    }

}