package com.williamlu.gankio.base

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
abstract class GankBaseActivity : BaseActivity() {
    /**
     * 是否需要检查权限 默认不检查
     * 通过 mPermissions 设置要申请的权限
     */
    override fun checkPermission(): Boolean {
        mPermissions = null
        return false
    }

}