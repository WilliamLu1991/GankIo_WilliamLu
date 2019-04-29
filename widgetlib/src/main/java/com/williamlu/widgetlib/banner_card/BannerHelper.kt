package com.williamlu.widgetlib.banner_card

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * @Author: WilliamLu
 * @Data: 2019/3/26
 * @Description:
 */
class BannerHelper {

    fun bannerInit(
        context: Context,
        list: ArrayList<String>,
        recycler: RecyclerView,
        indicator: BannerIndicator
    ): BannerAdapter {

        val adapter = BannerAdapter(context, list)
        //重写滚动速度
        val layoutManager = SmoothLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recycler.layoutManager = layoutManager
        recycler.setHasFixedSize(true)
        recycler.adapter = adapter

        //设置初始化时可向前滑动
        recycler.scrollToPosition(list.size * 10)
        //滑动翻页效果
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recycler)

        indicator.setNumber(list.size)

        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val i = layoutManager.findFirstVisibleItemPosition() % list.size
                    //得到指示器红点的位置
                    indicator.setPosition(i)
                }
            }
        })

        val scheduledExecutorService = Executors.newScheduledThreadPool(1)
        scheduledExecutorService.scheduleAtFixedRate(
            Runnable { recycler.smoothScrollToPosition(layoutManager.findFirstVisibleItemPosition() + 1) },
            3000,
            3000,
            TimeUnit.MILLISECONDS
        )

        return adapter
    }
}