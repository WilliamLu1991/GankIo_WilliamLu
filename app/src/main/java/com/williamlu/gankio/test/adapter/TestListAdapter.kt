package com.williamlu.gankio.test.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.williamlu.gankio.R
import com.williamlu.gankio.model.Movie

/**
 * @Author: WilliamLu
 * @Data: 2018/12/3
 * @Description:
 */
class TestListAdapter(data: List<Movie>) : BaseQuickAdapter<Movie, BaseViewHolder>(R.layout.item_test_movie, data) {
    override fun convert(helper: BaseViewHolder?, item: Movie?) {

    }
}