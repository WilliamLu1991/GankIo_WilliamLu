package com.williamlu.widgetlib.dialog

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.williamlu.widgetlib.R

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
class CustomAlertDialog private constructor() {
    companion object {
        private var INSTANCE: CustomAlertDialog? = null

        fun getInstance(): CustomAlertDialog {
            synchronized(CustomAlertDialog::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = CustomAlertDialog()
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
        return createAlertDialog(context, content, "", rightStr, true, true, onDialogSelectListener)
    }

    fun createAlertDialog(
        context: Context,
        content: String,
        leftStr: String,
        rightStr: String,
        onDialogSelectListener: OnDialogSelectListener
    ): Dialog {
        return createAlertDialog(context, content, leftStr, rightStr, true, true, onDialogSelectListener)
    }

    fun createAlertDialog(
        context: Context,
        content: String,
        rightStr: String,
        wouldCancel: Boolean,
        onDialogSelectListener: OnDialogSelectListener
    ): Dialog {
        return createAlertDialog(context, content, "", rightStr, wouldCancel, onDialogSelectListener)
    }

    fun createAlertDialog(
        context: Context,
        content: String,
        leftStr: String,
        rightStr: String,
        wouldCancel: Boolean,
        onDialogSelectListener: OnDialogSelectListener
    ): Dialog {
        return createAlertDialog(context, content, leftStr, rightStr, wouldCancel, true, onDialogSelectListener)
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
        leftStr: String,
        rightStr: String,
        wouldCancel: Boolean,
        isHappy: Boolean,
        onDialogSelectListener: OnDialogSelectListener
    ): Dialog {
        val dialog = Dialog(context, R.style.CustomLoadingDialog)
        val v = LayoutInflater.from(context).inflate(R.layout.dialog_alert, null) // 得到加载view
        val tvContent = v.findViewById(R.id.dialog_alert_tv_content) as TextView
        val tvLeft = v.findViewById(R.id.dialog_alert_tv_left) as TextView
        val tvRight = v.findViewById(R.id.dialog_alert_tv_right) as TextView
        val vLine = v.findViewById(R.id.dialog_alert_line) as View
        val ivIcon = v.findViewById(R.id.dialog_alert_iv) as ImageView

        tvContent.text = content

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

        if (isHappy) {
            ivIcon.setBackgroundResource(R.drawable.lib_ic_lulu)
        } else {
            ivIcon.setBackgroundResource(R.drawable.lib_ic_lulu)
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
        //fun onLeftSelect()
        fun onRightSelect()
    }

}