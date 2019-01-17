package com.williamlu.gankio.splash.adapter

import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup

/**
 * @Author: WilliamLu
 * @Data: 2019/1/7
 * @Description:
 */
class GuideVPAdapter(viewList: List<View>) : PagerAdapter() {
    private var viewList: List<View>? = null

    init {
        this.viewList = viewList
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
    }

    override fun getCount(): Int {
        if (viewList != null) {
            return viewList!!.size
        }
        return 0
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(viewList!![position])
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        (container as ViewPager).addView(viewList!![position], 0)
        return viewList!![position]
    }
}