package com.williamlu.gankio.base

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.orhanobut.logger.Logger
import com.tbruyelle.rxpermissions2.RxPermissions
import com.williamlu.gankio.GankIoApplation
import com.williamlu.gankio.event.ExitAppEvent
import com.williamlu.widgetlib.dialog.PermissionDialog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    /**
     * eventbus相应事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventbusListener(o: Any) {
        if (o is ExitAppEvent) {
            GankIoApplation.offNetworkReceiver()
            Logger.d(" 退出应用")
            ActivityCacheManager.getInstance().appExit(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    /**
     * 检查权限 及申请
     */
    private fun checkPermission(activity: Activity) {
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.BLUETOOTH)
                .subscribe({ granted ->
                    if (!granted) {
                        PermissionDialog.showMissingPermissionDialog(activity)
                    } else {

                    }
                }, { throwable -> throwable.printStackTrace() })
    }
}