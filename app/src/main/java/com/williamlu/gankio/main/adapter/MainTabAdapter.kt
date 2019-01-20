package com.williamlu.gankio.main.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.williamlu.gankio.main.view.AndroidFragment
import com.williamlu.gankio.main.view.IOSFragment
import com.williamlu.gankio.main.view.WelfareFragment

/**
 * @Author: WilliamLu
 * @Data: 2018/12/3
 * @Description:
 */
class MainTabAdapter(context: Context, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = AndroidFragment()
            1 -> fragment = WelfareFragment()
            2 -> fragment = IOSFragment()
        }
        return fragment!!
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Android"
            1 -> "福利"
            2 -> "IOS"
            else -> ""
        }
    }

}