package com.williamlu.toolslib

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @Author: WilliamLu
 * @Data: 2019/1/3
 * @Description:软键盘控制类
 */
object KeyboardUtils {
    /**
     * 避免输入法面板遮挡
     * <p>在manifest.xml中activity中设置</p>
     * <p>android:windowSoftInputMode="stateVisible|adjustResize"</p>
     */

    /**
     * 动态隐藏软键盘
     * @param activity activity
     */
    fun hideSoftInput(activity: Activity) {
        val view = activity.window.peekDecorView()
        if (view != null) {
            val inputmanger = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputmanger.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * 点击隐藏软键盘
     *
     * @param activity
     * @param view
     */
    fun hideKeyboard(activity: Activity, view: View) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}