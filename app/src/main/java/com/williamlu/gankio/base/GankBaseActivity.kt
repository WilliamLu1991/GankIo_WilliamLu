package com.williamlu.gankio.base

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
abstract class GankBaseActivity : BaseActivity(){
    override fun checkPermission(): Boolean {
        return false
    }
}