package com.williamlu.widgetlib.dialog

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.williamlu.widgetlib.R

/**
 * @Author: WilliamLu
 * @Date: 2018/11/21
 * @Description:
 */
object PermissionDialog {

    fun showMissingPermissionDialog(activity: Activity) {
        val builder = AlertDialog.Builder(activity, R.style.DialogMaterial)
        builder.setTitle("帮助")
        builder.setMessage(R.string.lack_permission)
        builder.setCancelable(false)

        // 拒绝, 退出应用
        builder.setNegativeButton("退出应用") { dialog, which -> activity.finish() }

        builder.setPositiveButton("设置") { dialog, which ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:" + activity.packageName)
            activity.startActivity(intent)
        }
        builder.show()
    }
}