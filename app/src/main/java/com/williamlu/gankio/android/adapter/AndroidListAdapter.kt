package com.williamlu.gankio.main.adapter

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.williamlu.gankio.R
import com.williamlu.gankio.base.BaseWebViewActivity
import com.williamlu.gankio.model.ClassifyDataBean
import com.williamlu.widgetlib.FullSheetDialog

/**
 * @Author: WilliamLu
 * @Data: 2018/12/3
 * @Description:
 */
class AndroidListAdapter(data: List<ClassifyDataBean>) : BaseQuickAdapter<ClassifyDataBean, BaseViewHolder>(R.layout.item_android_rv, data) {
    override fun convert(helper: BaseViewHolder?, item: ClassifyDataBean?) {
        if (item!!.images != null && item.images.size > 0) {
            Glide.with(mContext)
                    .load(item.images[0])
                    .placeholder(R.drawable.lib_ic_logo)
                    .error(R.drawable.lib_ic_logo)
                    .into(helper!!.getView(R.id.item_android_iv))
        } else {
            Glide.with(mContext)
                    .load(R.drawable.lib_ic_logo)
                    .placeholder(R.drawable.lib_ic_logo)
                    .error(R.drawable.lib_ic_logo)
                    .into(helper!!.getView(R.id.item_android_iv))
        }

        helper.setText(R.id.item_android_tv_des, item.desc)

        helper.itemView.setOnClickListener {
            BaseWebViewActivity.showClass(mContext, "Android", item.url)
        }

        helper.getView<ImageView>(R.id.item_android_iv).setOnClickListener {
            if (item!!.images == null || item.images.size == 0) {
                return@setOnClickListener
            }

            val fullSheetDialog = FullSheetDialog(mContext)
            val view = View.inflate(mContext, R.layout.view_big_image, null)

            Glide.with(mContext)
                    .load(item.images[0])
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