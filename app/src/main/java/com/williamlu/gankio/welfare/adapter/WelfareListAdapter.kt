package com.williamlu.gankio.main.adapter

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.williamlu.gankio.R
import com.williamlu.gankio.model.ClassifyDataBean
import com.williamlu.toolslib.RandomUtil
import com.williamlu.widgetlib.FullSheetDialog

/**
 * @Author: WilliamLu
 * @Data: 2018/12/3
 * @Description:
 */
class WelfareListAdapter(data: List<ClassifyDataBean>) : BaseQuickAdapter<ClassifyDataBean, BaseViewHolder>(R.layout.item_welfare_rv, data) {
    override fun convert(helper: BaseViewHolder?, item: ClassifyDataBean?) {
        val ivLayoutParams = helper!!.getView<ImageView>(R.id.item_main_iv).layoutParams
        ivLayoutParams.height = (mContext.resources.getDimensionPixelSize(R.dimen.dp_200) + Math.random() * 200).toInt()
        helper.getView<ImageView>(R.id.item_main_iv).layoutParams = ivLayoutParams

        helper.getView<TextView>(R.id.item_main_tv_desc).setBackgroundColor(Color.parseColor(RandomUtil.getRandomColorStr()))

        Glide.with(mContext)
                .load(item!!.url)
                .placeholder(R.drawable.lib_ic_logo)
                .error(R.drawable.lib_ic_logo)
                .into(helper.getView(R.id.item_main_iv))

        helper.setText(R.id.item_main_tv_desc, item.desc)

        helper.itemView.setOnClickListener {
            val fullSheetDialog = FullSheetDialog(mContext)
            val view = View.inflate(mContext, R.layout.view_big_image, null)

            Glide.with(mContext)
                    .load(item.url)
                    .placeholder(R.drawable.lib_ic_logo)
                    .error(R.drawable.lib_ic_logo)
                    .into(view.findViewById<ImageView>(R.id.viewimage_iv_img))

            fullSheetDialog.setContentView(view)
            fullSheetDialog.show()


            view.findViewById<ImageView>(R.id.viewimage_iv_close).setOnClickListener {
                fullSheetDialog.dismiss()
            }
        }
    }

}