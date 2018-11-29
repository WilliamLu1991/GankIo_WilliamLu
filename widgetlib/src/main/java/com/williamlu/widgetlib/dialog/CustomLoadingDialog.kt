package com.williamlu.widgetlib.dialog

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.williamlu.widgetlib.R

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
object CustomLoadingDialog {

    fun createLoadingDialog(context: Context): Dialog {
        return createLoadingDialog(context, "")
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    fun createLoadingDialog(context: Context, msg: String): Dialog {

        val inflater = LayoutInflater.from(context)
        val v = inflater.inflate(R.layout.dialog_load, null) // 得到加载view
        val layout = v.findViewById(R.id.dialog_view) as LinearLayout // 加载布局
        // main.xml中的ImageView
        val spaceshipImage = v.findViewById(R.id.img) as ImageView
        val tipTextView = v.findViewById(R.id.tipTextView) as TextView // 提示文字
        /*// 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);*/
        Glide.with(context).load(R.drawable.loading).into(spaceshipImage)
        if (TextUtils.isEmpty(msg)) {
            tipTextView.text = "加载中..."
        } else {
            tipTextView.text = msg // 设置加载信息
        }

        val loadingDialog = Dialog(context, R.style.CustomLoadingDialog) // 创建自定义样式dialog

        loadingDialog.setCancelable(true) // 不可以用“返回键”取消
        loadingDialog.setContentView(layout, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)) // 设置布局
        return loadingDialog

    }

}