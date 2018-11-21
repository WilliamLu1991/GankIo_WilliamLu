package com.williamlu.gankio

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.FrameLayout
import com.williamlu.datalib.api.DouBanMovieService
import com.williamlu.gankio.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var totalHeight: Float = 0.toFloat()      //总高度
    private var toolBarHeight: Float = 0.toFloat()    //toolBar高度
    private var offSetHeight: Float = 0.toFloat()     //总高度 -  toolBar高度  布局位移值
    private var tvSearchHeight: Float = 0.toFloat()         //搜索框高度
    private var params: FrameLayout.LayoutParams? = null
    private var llHeightOffScale: Float = 0.toFloat()     //高度差比值
    private var llOffDistance: Float = 0.toFloat()        //距离差
    private var llOffDistanceScale: Float = 0.toFloat()   //距离差比值

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_main
    }

    @SuppressLint("CheckResult")
    override fun initView(savedInstanceState: Bundle?) {
        DouBanMovieService.getInstance()
                .getMovieTop250(1, 20)
                .subscribe {

                }

        setSupportActionBar(main_toolbar)

        totalHeight = resources.getDimension(R.dimen.app_bar_height)
        toolBarHeight = resources.getDimension(R.dimen.tool_bar_height)
        offSetHeight = totalHeight - toolBarHeight

        /**
         *   移动效果值／最终效果值 =  移动距离／ 能移动总距离（确定）
         */
        main_app_bar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            //第一次进入获取高度，以及差值 高度差比值
            if (tvSearchHeight == 0f) {
                tvSearchHeight = main_fl_search.measuredHeight.toFloat()
                params = main_fl_search.layoutParams as FrameLayout.LayoutParams?

                //算出高度偏移量比值  相对与llHeight
                llHeightOffScale = 1.0f - toolBarHeight / tvSearchHeight

                //得到滑动差值 就是布局marginTop
                llOffDistance = params!!.topMargin.toFloat()
                //得到滑动比值
                llOffDistanceScale = llOffDistance / offSetHeight

            }

            //计算maigintop值
            val distance = llOffDistance - -verticalOffset * llOffDistanceScale

            params!!.setMargins(-verticalOffset * 12 / 10, distance.toInt(), 0, 0)

            //System.out.println("verticalOffset:" + verticalOffset + ",distance:" + distance + ",alpha:");

            main_fl.requestLayout()
        }
    }

}
