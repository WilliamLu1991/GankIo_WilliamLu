package com.williamlu.toolslib

import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment

/**
 * @Author: WilliamLu
 * @Date: 2018/11/21
 * @Description:SdcardUtil,SDcard相关逻辑的工具类
 */
object SdcardUtil {
    private val EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE"
    private val ERROR_NO_PERMISSION = "没有SD卡的读写权限"
    private val ERROR_NO_SDCARD = "没有SD卡"
    private val ERROR_SDCARD_FREE_SPACE_NOT_ENOUGH = "SD卡可用空间不足"

    /**
     * SD卡是否可用,包括:
     * 1,SD卡的写权限,see[.hasExternalStoragePermission]
     * 2,SD卡是否已挂载,see[.isSDCARDReadyReadWrite]
     * 3,SD卡的可用的存储空间是否足够,see[.isSDCardHasEnoughFreeSpace]
     *
     * @return boolean | true:可用;false:不可用
     */
    fun isSDCardUsable(context: Context): Boolean {
        return (SdcardUtil.hasExternalStoragePermission(context)
                && SdcardUtil.isSDCARDReadyReadWrite()
                && SdcardUtil.isSDCardHasEnoughFreeSpace())
    }

    /**
     * 检查SD卡可用状态,同时提供UI交互
     * 1,SD卡的写权限,see[.hasExternalStoragePermission],Toast文案为[.ERROR_NO_PERMISSION]
     * 2,SD卡是否已挂载,see[.isSDCARDReadyReadWrite],Toast文案为[.ERROR_NO_SDCARD]
     * 3,SD卡的可用的存储空间是否足够,see[.isSDCardHasEnoughFreeSpace],Toast文案为[.ERROR_SDCARD_FREE_SPACE_NOT_ENOUGH]
     *
     * @return boolean | true:可用;false:不可用
     */
    fun checkSDCardUsableWithToast(context: Context): String {
        if (!SdcardUtil.hasExternalStoragePermission(context)) {
            return ERROR_NO_PERMISSION
        }
        if (!SdcardUtil.isSDCARDReadyReadWrite()) {
            return ERROR_NO_SDCARD
        }
        return if (!SdcardUtil.isSDCardHasEnoughFreeSpace()) {
            ERROR_SDCARD_FREE_SPACE_NOT_ENOUGH
        } else ""
    }

    /**
     * SD卡是否已经挂载
     *
     * @return boolean
     */
    fun isSDCARDReadyReadWrite(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

    /**
     * 检查SD卡的剩余容量是否大于10MB
     *
     * @return boolean | true:大于5MB;false:小于5MB
     */
    fun isSDCardHasEnoughFreeSpace(): Boolean {
        val file = Environment.getExternalStorageDirectory() ?: return false
        val availableSizeInBytes = file.freeSpace
        val megAvailable = availableSizeInBytes / 1048576

        return megAvailable >= 5
    }

    fun hasExternalStoragePermission(context: Context?): Boolean {
        if (context == null) {
            return false
        }
        val result = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION) == PackageManager.PERMISSION_GRANTED
        if (!result) {
        }
        return result
    }
}