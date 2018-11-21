package com.williamlu.gankio

import android.app.ActivityManager
import android.content.Context
import java.util.ArrayList

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 
 */
class ActivityCacheManager {

    companion object {
        private val instance = ActivityCacheManager()

        fun getInstance(): ActivityCacheManager {
            return instance
        }
    }

    private val activityList = ArrayList<BaseActivity>()

    fun addActivity(activity: BaseActivity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity)
        }
        activityList.add(activity)
    }

    fun removeActivity(activity: BaseActivity) {
        activityList.remove(activity)
    }

    fun getTopActivity(): BaseActivity? {
        return if (activityList.size > 0) {
            activityList[activityList.size - 1]
        } else {
            null
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        var i = 0
        val size = activityList.size
        while (i < size) {
            if (null != activityList[i]) {
                activityList[i].finish()
            }
            i++
        }
        activityList.clear()
    }

    /**
     * 应用程序完全退出
     *
     * @param context 当前上下文
     */
    fun appExit(context: Context) {
        try {
            finishAllActivity()
            val activityMgr = context.getSystemService(Context
                    .ACTIVITY_SERVICE) as ActivityManager
            activityMgr.killBackgroundProcesses(context.packageName)
            System.exit(0)
        } catch (e: Exception) {
        }

    }
}