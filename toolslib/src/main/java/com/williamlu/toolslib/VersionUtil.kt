package com.williamlu.toolslib

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

/**
 * @Author: WilliamLu
 * @Date: 2018/11/21
 * @Description:
 */
object VersionUtil {
    /**
     * 获取版本号versionCode
     *
     * @return
     */
    fun getVersionCode(context: Context): String {
        val packageManager = context.packageManager
        val packageInfo: PackageInfo
        var versionCode = ""
        try {
            packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            versionCode = packageInfo.versionCode.toString() + ""
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return versionCode
    }

    /**
     * 获取版本名versionName
     *
     * @return
     */
    fun getVersionName(context: Context): String {
        val packageManager = context.packageManager
        val packageInfo: PackageInfo
        var versionName = ""
        try {
            packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            versionName = packageInfo.versionName + ""
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return versionName
    }
}