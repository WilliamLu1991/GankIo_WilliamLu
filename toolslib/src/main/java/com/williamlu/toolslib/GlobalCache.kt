package com.williamlu.toolslib

import android.content.Context

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
class GlobalCache private constructor() {
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
        private var INSTANCE: GlobalCache? = null

        fun getInstance(): GlobalCache {
            synchronized(GlobalCache::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = GlobalCache()
                }
            }
            return INSTANCE!!
        }

        fun getContext(): Context {
            return getInstance().mContext!!
        }
    }

}