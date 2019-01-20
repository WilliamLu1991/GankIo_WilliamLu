package com.williamlu.gankio.main.view

import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import com.kingnet.creditclient.main.adapter.MainBannerAdapter
import com.williamlu.gankio.R
import com.williamlu.gankio.base.GankIoBaseActivity
import com.williamlu.gankio.base.GlideApp
import com.williamlu.gankio.event.ExitAppEvent
import com.williamlu.gankio.main.adapter.MainTabAdapter
import com.williamlu.gankio.main.contract.MainContract
import com.williamlu.gankio.main.presenter.MainPresenter
import com.williamlu.gankio.model.ClassifyDataBean
import com.williamlu.toolslib.ToastUtils
import com.williamlu.widgetlib.dialog.CustomAlertDialog
import com.williamlu.widgetlib.dialog.FullSheetDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:首页
 */
class MainActivity : GankIoBaseActivity(), MainContract.View {
    private var mMainPresenter: MainPresenter? = null
    private var mBannerAdapter: MainBannerAdapter? = null
    private var mPageIndex: Int = 1

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_main
    }

    override fun initPresenter() {
        MainPresenter(this)
    }

    override fun setMainPresenter(presenter: MainPresenter) {
        mMainPresenter = presenter
    }

    override fun processComplete(data: List<ClassifyDataBean>) {
        //初始化banner
        initBanner(data)
    }

    override fun initView() {
        mBaseToolBarHelper!!.setTitleName(resources.getString(R.string.app_name)).showLeftView().setBgImg(R.drawable.lib_ic_personal)
        mPageIndex = 1
        mMainPresenter!!.getClassifyData("福利", mPageIndex.toString())

        main_viewpager.adapter = MainTabAdapter(this, supportFragmentManager)
        main_tablayout.setupWithViewPager(main_viewpager)

    }

    override fun initListener() {
        mBaseToolBarHelper!!.getLeftView().setOnClickListener {
            if (main_dl.isDrawerOpen(Gravity.LEFT)) {
                main_dl.closeDrawer(Gravity.LEFT)
            } else {
                main_dl.openDrawer(Gravity.LEFT)
            }
        }
    }

    private fun initBanner(data: List<ClassifyDataBean>) {
        var subList = data
        if (subList.size > 4) {
            subList = data.subList(0, 3)
        }
        if (mBannerAdapter == null) {
            mBannerAdapter = MainBannerAdapter(subList)
            main_bl_recycler.setAdapter(mBannerAdapter)
        } else {
            mBannerAdapter!!.setNewData(subList)
        }
        main_bl_recycler.setAutoPlaying(true)
        mBannerAdapter!!.setOnItemClickListener { adapter, view, position ->
            val fullSheetDialog = FullSheetDialog(this)
            val view = View.inflate(this, R.layout.view_big_image, null)

            GlideApp.with(this)
                    .load(subList[position].url)
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {

            CustomAlertDialog.getInstance()
                    .createAlertDialog(this, "确定要退出应用吗", "再看看", "狠心退出", true, false,
                            object : CustomAlertDialog.OnDialogSelectListener {
                                override fun onRightSelect() {
                                    ToastUtils.dismissToast()
                                    EventBus.getDefault().post(ExitAppEvent(this@MainActivity))
                                }

                            })

            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
