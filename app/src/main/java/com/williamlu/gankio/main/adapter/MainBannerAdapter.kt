package com.kingnet.creditclient.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.williamlu.gankio.R
import com.williamlu.gankio.base.GlideApp
import com.williamlu.gankio.model.ClassifyDataBean

/**
 * @Author: WilliamLu
 * @Data: 2018/12/3
 * @Description:
 */
class MainBannerAdapter(data: List<ClassifyDataBean>) : BaseQuickAdapter<ClassifyDataBean, BaseViewHolder>(R.layout.item_main_banner, data) {
    override fun convert(helper: BaseViewHolder?, item: ClassifyDataBean) {
        GlideApp.with(mContext)
            .load(item.url)
            .placeholder(R.drawable.lib_ic_logo)
            .error(R.drawable.lib_ic_logo)
            .into(helper!!.getView(R.id.item_main_banner_iv))
    }
}