package com.williamlu.gankio.main.view

import android.content.Intent
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.umeng.socialize.utils.DeviceConfig.context
import com.williamlu.gankio.R
import com.williamlu.gankio.base.GankIoBaseActivity
import com.williamlu.gankio.event.ExitAppEvent
import com.williamlu.gankio.login.view.LoginActivity
import com.williamlu.gankio.main.adapter.MainTabAdapter
import com.williamlu.gankio.main.contract.MainContract
import com.williamlu.gankio.main.presenter.MainPresenter
import com.williamlu.gankio.model.ClassifyDataBean
import com.williamlu.toolslib.ToastUtils
import com.williamlu.widgetlib.CustomAlertDialog
import com.williamlu.widgetlib.FullSheetDialog
import com.williamlu.widgetlib.banner_card.BannerAdapter
import com.williamlu.widgetlib.banner_card.BannerHelper
import com.williamlu.widgetlib.banner_card.BannerIndicator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_banner_card.*
import kotlinx.android.synthetic.main.view_mine.*
import org.greenrobot.eventbus.EventBus

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:首页
 */
class MainActivity : GankIoBaseActivity(), MainContract.View {
    private var mMainPresenter: MainPresenter? = null
    private var mBannerAdapter: BannerAdapter? = null
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
        mBaseToolbar!!.setBackgroundResource(R.drawable.transparent_bg)
        mBaseToolBarHelper!!.setTitleName(resources.getString(R.string.app_name)).showLeftView().setBgImg(R.drawable.lib_ic_personal)
        mPageIndex = 1
        mMainPresenter!!.getClassifyData("福利", mPageIndex.toString())
        mSwipeRl.setProgressViewEndTarget(false, resources.getDimensionPixelSize(R.dimen.dp_85))

        main_viewpager.adapter = MainTabAdapter(this, supportFragmentManager)
        main_tablayout.setupWithViewPager(main_viewpager)

    }

    override fun initListener() {
        mSwipeRl.setOnRefreshListener {
            dismissSwipeRl()
        }

        mBaseToolBarHelper!!.getLeftView().setOnClickListener {
            if (main_dl.isDrawerOpen(Gravity.LEFT)) {
                main_dl.closeDrawer(Gravity.LEFT)
            } else {
                main_dl.openDrawer(Gravity.LEFT)
            }
        }

        mine_bt_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        main_appbarlayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == 0) {
                mSwipeRl.isEnabled = true
            } else {
                mSwipeRl.isEnabled = false
            }
        }
    }

    private fun initBanner(data: List<ClassifyDataBean>) {
        var subList = data
        if (subList.size > 4) {
            subList = data.subList(0, 3)
        }
        var imageList = ArrayList<String>()
        subList.forEach {
            imageList.add(it.url)
        }
        if (mBannerAdapter == null) {
            mBannerAdapter = BannerHelper().bannerInit(
                    this,
                    imageList,
                    banner_card_rv!!,
                    banner_card_indicator!!
            )
        } else {
            mBannerAdapter!!.setNewData(imageList)
            banner_card_indicator!!.setNumber(imageList.size)
        }

        mBannerAdapter!!.setOnItemClickListener(object : BannerAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val fullSheetDialog = FullSheetDialog(this@MainActivity)
                val view = View.inflate(this@MainActivity, R.layout.view_big_image, null)

                Glide.with(this@MainActivity)
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
        })
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
