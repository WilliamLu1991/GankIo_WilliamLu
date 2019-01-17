package com.williamlu.gankio.splash.view

import android.content.Intent
import android.support.v4.view.ViewPager
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.williamlu.gankio.AppConstant
import com.williamlu.gankio.R
import com.williamlu.gankio.base.GankIoBaseActivity
import com.williamlu.gankio.event.ExitAppEvent
import com.williamlu.gankio.main.view.MainActivity
import com.williamlu.gankio.splash.adapter.GuideVPAdapter
import com.williamlu.toolslib.SpUtils
import com.williamlu.toolslib.ToastUtils
import com.williamlu.widgetlib.dialog.CustomAlertDialog
import kotlinx.android.synthetic.main.activity_guide.*
import org.greenrobot.eventbus.EventBus

class GuideActivity : GankIoBaseActivity() {
    private val mSpUtils = SpUtils.getInstance(AppConstant.SpConstant.USER_INFO)
    private var viewList = ArrayList<View>()
    private var mGuideVPAdapter: GuideVPAdapter? = null
    private var mIvList = arrayOf(R.drawable.lib_ic_lulu, R.drawable.lib_ic_empty, R.drawable.lib_ic_search)

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_guide
    }

    override fun initPresenter() {
    }

    override fun initView() {
        mSpUtils.put(AppConstant.SpConstant.USER_IS_FIRST_START_APP, false)

        for (i in 0 until mIvList.size) {
            var view = LayoutInflater.from(this).inflate(R.layout.view_guide_viewpager, null)
            view.findViewById<ImageView>(R.id.guidevp_iv).setBackgroundResource(mIvList[i])
            viewList.add(view)
        }

        mGuideVPAdapter = GuideVPAdapter(viewList)
        guide_vp.adapter = mGuideVPAdapter

        setDotsSelected(0)

    }

    override fun initListener() {
        guide_vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position == mIvList.size - 1) {
                    viewList[position].findViewById<Button>(R.id.guidevp_bt_go).visibility = View.VISIBLE
                    viewList[position].findViewById<Button>(R.id.guidevp_bt_go).setOnClickListener {
                        startActivity(Intent(this@GuideActivity, MainActivity::class.java))
                        finish()
                    }
                }
                setDotsSelected(position)
            }
        })
    }

    private fun setDotsSelected(position: Int) {
        for (i in 0 until mIvList.size) {
            guide_ll_dot.getChildAt(i).isEnabled = i == position
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {

            CustomAlertDialog.getInstance()
                .createAlertDialog(this, "确定要退出应用吗", "再看看", "狠心退出", true, false,
                    object : CustomAlertDialog.OnDialogSelectListener {
                        override fun onRightSelect() {
                            ToastUtils.dismissToast()
                            EventBus.getDefault().post(ExitAppEvent(this@GuideActivity))
                        }
                    })

            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
