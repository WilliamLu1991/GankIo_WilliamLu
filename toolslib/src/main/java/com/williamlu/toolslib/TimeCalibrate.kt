package com.williamlu.toolslib

import android.os.SystemClock
import java.util.*

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 
 */


object TimeCalibrate {
    private var currentTag: Long = 0 //本地时间
    private var serviceTime: Long = 0 //服务器时间
    private var succedFetchedTime = false //是否成功获取过服务器时间
    private var localTimeIsOK = true  //本地时间是否有效,默认是有效的

    /**
     * 确定服务器时间和本地时间是否一致，60秒内认为一致
     *
     * @param inputTime long
     */
    fun initServerTime(inputTime: Long) {
        if (!succedFetchedTime || !localTimeIsOK) {
            serviceTime = inputTime
            succedFetchedTime = true
            currentTag = SystemClock.elapsedRealtime()
            localTimeIsOK = Math.abs(System.currentTimeMillis() - serviceTime) < 60000
        }
    }

    fun isSuccedFetchedTime(): Boolean {
        return succedFetchedTime
    }

    fun getCurrentCalendar(): Date {
        val s = Date()
        //        Calendar currentCalendar = DateUtil.getLocalCalendar();
        if (!localTimeIsOK) {
            s.time = serviceTime + SystemClock.elapsedRealtime() - currentTag
        }
        return s
    }
}