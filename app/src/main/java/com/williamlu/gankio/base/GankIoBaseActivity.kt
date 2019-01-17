package com.williamlu.gankio.base

import android.os.Bundle
import com.umeng.analytics.MobclickAgent

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
abstract class GankIoBaseActivity : BaseActivity() {
    override fun onInitParams(bundle: Bundle?) {}

    /**
     * 是否需要检查权限 默认不检查
     * 通过 mPermissions 设置要申请的权限
     */
    override fun checkPermission(): Boolean {
        mPermissions = null
        return false
    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

}