package com.williamlu.widgetlib

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
class AppUpgradeDialog private constructor() {
    companion object {
        private var INSTANCE: AppUpgradeDialog? = null

        fun getInstance(): AppUpgradeDialog {
            synchronized(AppUpgradeDialog::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = AppUpgradeDialog()
                }
            }
            return INSTANCE!!
        }
    }

    fun createUpgradeDialog(
            context: Context,
            appVersion: String,
            content: String,
            onDialogSelectListener: OnDialogUpgradeListener
    ): Dialog {
        return createUpgradeDialog(context, appVersion, content, true, onDialogSelectListener)
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    fun createUpgradeDialog(
            context: Context,
            appVersion: String,
            content: String,
            wouldCancel: Boolean,
            onDialogSelectListener: OnDialogUpgradeListener
    ): Dialog {
        val dialog = Dialog(context, R.style.CustomLoadingDialog)
        val v = LayoutInflater.from(context).inflate(R.layout.dialog_app_upgrade, null)
        val tvVersion = v.findViewById(R.id.dialog_upgrade_tv_version) as TextView
        val tvContent = v.findViewById(R.id.dialog_upgrade_tv_content) as TextView
        val btnGo = v.findViewById(R.id.dialog_upgrade_btn_go) as Button
        val ivClose = v.findViewById(R.id.dialog_upgrade_iv_close) as ImageView

        tvVersion.text = appVersion
        tvContent.text = content

        dialog.setCanceledOnTouchOutside(wouldCancel)
        dialog.setCancelable(wouldCancel)
        if (wouldCancel) {
            ivClose.visibility = View.VISIBLE
        } else {
            ivClose.visibility = View.GONE
        }

        ivClose.setOnClickListener {
            if (wouldCancel) {
                dialog.dismiss()
            }
        }

        btnGo.setOnClickListener {
            onDialogSelectListener.onUpgradeSelect()
            if (wouldCancel) {
                dialog.dismiss()
            }
        }

        dialog.setContentView(
                v, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        ) // 设置布局
        dialog.show()
        return dialog
    }

    private var onDialogUpgradeListener: OnDialogUpgradeListener? = null

    fun setOnDialogSelectListener(onDialogUpgradeListener: OnDialogUpgradeListener) {
        this.onDialogUpgradeListener = onDialogUpgradeListener
    }

    interface OnDialogUpgradeListener {
        fun onUpgradeSelect()
    }

}