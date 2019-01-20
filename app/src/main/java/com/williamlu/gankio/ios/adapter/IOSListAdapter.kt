package com.williamlu.gankio.main.adapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.williamlu.gankio.R
import com.williamlu.gankio.base.BaseWebViewActivity
import com.williamlu.gankio.base.GlideApp
import com.williamlu.gankio.model.ClassifyDataBean
import com.williamlu.widgetlib.dialog.FullSheetDialog

/**
 * @Author: WilliamLu
 * @Data: 2018/12/3
 * @Description:
 */
class IOSListAdapter(data: List<ClassifyDataBean>) : BaseQuickAdapter<ClassifyDataBean, BaseViewHolder>(R.layout.item_ios_rv, data) {
    override fun convert(helper: BaseViewHolder?, item: ClassifyDataBean?) {
        if (item!!.images != null && item.images.size > 0) {
            GlideApp.with(mContext)
                    .load(item.images[0])
                    .placeholder(R.drawable.lib_ic_logo)
                    .error(R.drawable.lib_ic_logo)
                    .into(helper!!.getView(R.id.item_ios_iv))
        } else {
            GlideApp.with(mContext)
                    .load(R.drawable.lib_ic_logo)
                    .placeholder(R.drawable.lib_ic_logo)
                    .error(R.drawable.lib_ic_logo)
                    .into(helper!!.getView(R.id.item_ios_iv))
        }

        helper.setText(R.id.item_ios_tv_des, item.desc)

        helper.itemView.setOnClickListener {
            BaseWebViewActivity.showClass(mContext, "IOS", item.url)
        }

        helper.getView<ImageView>(R.id.item_ios_iv).setOnClickListener {
            if (item!!.images == null || item.images.size == 0) {
                return@setOnClickListener
            }

            val fullSheetDialog = FullSheetDialog(mContext)
            val view = View.inflate(mContext, R.layout.view_big_image, null)

            GlideApp.with(mContext)
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