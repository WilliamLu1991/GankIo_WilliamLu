package com.williamlu.gankio.home.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.williamlu.gankio.R
import com.williamlu.gankio.base.GlideApp
import com.williamlu.gankio.model.Movie

/**
 * @Author: WilliamLu
 * @Data: 2018/12/3
 * @Description:
 */
class MainListAdapter(data: List<Movie>) : BaseQuickAdapter<Movie, BaseViewHolder>(R.layout.item_main_movie, data) {
    override fun convert(helper: BaseViewHolder?, item: Movie?) {
        GlideApp.with(mContext).load(item!!.images.large).into(helper!!.getView(R.id.item_main_iv))
        helper.setText(R.id.item_main_tv_name, item.title)
        helper.setText(R.id.item_main_tv_score, item.rating.average.toString())
    }
}