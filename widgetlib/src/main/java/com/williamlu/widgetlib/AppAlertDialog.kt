package com.williamlu.widgetlib

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
class AppAlertDialog private constructor() {
    companion object {
        private var INSTANCE: AppAlertDialog? = null

        fun getInstance(): AppAlertDialog {
            synchronized(AppAlertDialog::class.java) {
                if (INSTANCE == null) {
                    INSTANCE =
                            AppAlertDialog()
                }
            }
            return INSTANCE!!
        }
    }

    fun createAlertDialog(
            context: Context,
            content: String,
            rightStr: String,
            onDialogSelectListener: OnDialogSelectListener
    ): Dialog {
        return createAlertDialog(context, content, "", "", rightStr, true, onDialogSelectListener)
    }

    fun createAlertDialog(
            context: Context,
            content: String,
            leftStr: String,
            rightStr: String,
            onDialogSelectListener: OnDialogSelectListener
    ): Dialog {
        return createAlertDialog(context, content, "", leftStr, rightStr, true, onDialogSelectListener)
    }

    fun createAlertDialog(
            context: Context,
            content: String,
            rightStr: String,
            wouldCancel: Boolean,
            onDialogSelectListener: OnDialogSelectListener
    ): Dialog {
        return createAlertDialog(context, content, "", "", rightStr, wouldCancel, onDialogSelectListener)
    }

    fun createAlertDialog(
            context: Context,
            content: String,
            content2: String,
            leftStr: String,
            rightStr: String,
            onDialogSelectListener: OnDialogSelectListener
    ): Dialog {
        return createAlertDialog(context, content, content2, leftStr, rightStr, true, onDialogSelectListener)
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    fun createAlertDialog(
            context: Context,
            content: String,
            content2: String,
            leftStr: String,
            rightStr: String,
            wouldCancel: Boolean,
            onDialogSelectListener: OnDialogSelectListener
    ): Dialog {
        val dialog = Dialog(context, R.style.CustomLoadingDialog)
        val v = LayoutInflater.from(context).inflate(R.layout.dialog_app_alert, null) // 得到加载view
        val tvContent = v.findViewById(R.id.dialog_app_alert_tv_content) as TextView
        val flContent2 = v.findViewById(R.id.dialog_app_alert_fl_content2) as FrameLayout
        val tvContent2 = v.findViewById(R.id.dialog_app_alert_tv_content2) as TextView
        val tvLeft = v.findViewById(R.id.dialog_app_alert_tv_left) as TextView
        val tvRight = v.findViewById(R.id.dialog_app_alert_tv_right) as TextView
        val vLine = v.findViewById(R.id.dialog_app_alert_line) as View

        tvContent.text = content

        if (TextUtils.isEmpty(content2)) {
            flContent2.visibility = View.GONE
        } else {
            flContent2.visibility = View.VISIBLE
            tvContent2.text = content2
        }

        if (TextUtils.isEmpty(leftStr)) {
            tvLeft.visibility = View.GONE
            vLine.visibility = View.GONE
            tvRight.visibility = View.VISIBLE
            tvRight.text = rightStr
        } else {
            tvLeft.visibility = View.VISIBLE
            vLine.visibility = View.VISIBLE
            tvRight.visibility = View.VISIBLE
            tvRight.text = rightStr
        }
        tvLeft.text = leftStr
        tvRight.text = rightStr

        dialog.setCanceledOnTouchOutside(wouldCancel)
        dialog.setCancelable(wouldCancel)

        flContent2.setOnClickListener {
            onDialogSelectListener.onContent2Select()
            if (wouldCancel) {
                dialog.dismiss()
            }
        }

        tvLeft.setOnClickListener {
            //onDialogSelectListener!!.onLeftSelect()
            if (wouldCancel) {
                dialog.dismiss()
            }

        }
        tvRight.setOnClickListener {
            onDialogSelectListener.onRightSelect()
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

    private var onDialogSelectListener: OnDialogSelectListener? = null

    fun setOnDialogSelectListener(onDialogSelectListener: OnDialogSelectListener) {
        this.onDialogSelectListener = onDialogSelectListener
    }

    interface OnDialogSelectListener {
        fun onContent2Select()
        fun onRightSelect()
    }

}