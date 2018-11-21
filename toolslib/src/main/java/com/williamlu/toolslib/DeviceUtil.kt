package com.williamlu.toolslib

import android.Manifest
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.DisplayMetrics
import com.williamlu.toolslib.DateUtils.DATE_VISUAL14FORMAT
import com.williamlu.toolslib.DateUtils.getCurrentDateTime
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*

/*
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 
 */
object DeviceUtil {
    /**
     * 获取IMEI,并检测IMEI的正确性,如果IMEI不正确,则返回空字符串
     *
     * @param context Context
     * @return String
     */
    fun getImei(context: Context): String {
        val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var imei = ""
        try {
            val packageManager = context.packageManager
            if (PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(Manifest.permission.READ_PHONE_STATE, context.packageName)) {
                imei = manager.deviceId
            }
            if (!DeviceUtil.checkIMEI(imei)) {
                imei = ""
            }
        } catch (e: Exception) {
        }

        return imei
    }

    /**
     * 获取IP地址
     *
     * @return String
     */
    fun getLocalIpAddress(): String {
        try {
            val en = NetworkInterface
                    .getNetworkInterfaces()
            if (en != null) {
                while (en.hasMoreElements()) {
                    val intf = en.nextElement()
                    if (!intf.supportsMulticast() || !intf.isUp) {
                        continue
                    }
                    val enumIpAddr = intf
                            .inetAddresses
                    while (enumIpAddr.hasMoreElements()) {

                        val inetAddress = enumIpAddr.nextElement()
                        if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address && !inetAddress.getHostAddress().startsWith("127.0")) {
                            return inetAddress.getHostAddress()
                        }
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     * 获取IP地址列表
     *
     * @return List<String>
    </String> */
    fun getLocalIpList(): List<String> {
        val ipList = ArrayList<String>()
        try {
            val en = NetworkInterface
                    .getNetworkInterfaces()
            if (en != null) {
                while (en.hasMoreElements()) {
                    val intf = en.nextElement()
                    if (!intf.supportsMulticast() || !intf.isUp) {
                        continue
                    }
                    val enumIpAddr = intf
                            .inetAddresses
                    while (enumIpAddr.hasMoreElements()) {
                        val inetAddress = enumIpAddr.nextElement()
                        if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address && !inetAddress.getHostAddress().startsWith("127.0")) {
                            ipList.add(inetAddress.getHostAddress())
                        }
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
        }

        return ipList
    }

    /**
     * 获取Mac地址,先获取Wifi的mac地址,如果无法获取到,再返回以太网的网卡mac
     *
     * @param context String
     * @return String
     */
    fun getMacAddress(context: Context): String {
        var macAddress = getMacaddressWifi(context)
        if (TextUtils.isEmpty(macAddress)) {
            macAddress = getMacAddressEthernet()
        }
        return macAddress
    }

    fun getMacaddressWifi(context: Context): String {
        var macAddress = ""
        val packageManager = context.packageManager
        try {
            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            if (PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(Manifest.permission.ACCESS_WIFI_STATE, context.packageName)) {
                if (wifiManager != null && wifiManager.connectionInfo != null) {
                    macAddress = wifiManager.connectionInfo.macAddress
                }
            }
            if (!DeviceUtil.checkMacAddress(macAddress)) {
                macAddress = ""
            }
        } catch (e: Exception) {
        }

        return macAddress
    }

    /**
     * 获取以太网卡的mac地址
     *
     * @return String
     */
    fun getMacAddressEthernet(): String {
        val result = ShellUtil.execCommand("cat /sys/class/net/eth0/address", ShellUtil.hasRootPermission())
        return result.responseMsg
    }

    /**
     * 获取DeviceID
     *
     * @param context Context
     * @return String
     */
    fun getDeviceID(context: Context): String? {
        var deviceId = ""
        val packageManager = context.packageManager
        if (PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(Manifest.permission.READ_PHONE_STATE, context.packageName)) {

            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            deviceId = tm.deviceId
            if (!TextUtils.isEmpty(deviceId) && deviceId.startsWith("0000000")) {
                return ""
            }
        }
        return deviceId
    }

    /**
     * 获取序列号
     *
     * @return String
     */
    fun getSerialNO(): String? {
        var serial = Build.SERIAL
        if ("unknown".equals(serial, ignoreCase = true) || "none".equals(serial, ignoreCase = true) || "null".equals(serial, ignoreCase = true)) {
            serial = ""
        }
        return serial
    }

    /**
     * 生成一个UUID
     *
     * @return String
     */
    fun generateUUID(): String {
        return UUID.randomUUID().toString()
    }

    /**
     * 获取屏幕信息
     *
     * @param context Activity
     * @return DisplayMetrics
     */
    fun getScreenMetrics(context: Activity): DisplayMetrics {
        val metric = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(metric)
        return metric
    }

    /**
     * 打印全部系统信息
     *
     * @param context Context
     * @return String
     */
    fun printSystemInfo(context: Context): String {
        val time = getCurrentDateTime(DATE_VISUAL14FORMAT)
        val sb = StringBuilder()
        sb.append("_______  系统信息  ").append(time).append(" ______________")
        sb.append("\nID                 :").append(Build.ID)
        sb.append("\nBRAND              :").append(Build.BRAND)
        sb.append("\nMODEL              :").append(Build.MODEL)
        sb.append("\nRELEASE            :").append(Build.VERSION.RELEASE)
        sb.append("\nSDK                :").append(Build.VERSION.SDK)

        sb.append("\n_______ OTHER _______")
        sb.append("\nBOARD              :").append(Build.BOARD)
        sb.append("\nPRODUCT            :").append(Build.PRODUCT)
        sb.append("\nDEVICE             :").append(Build.DEVICE)
        sb.append("\nFINGERPRINT        :").append(Build.FINGERPRINT)
        sb.append("\nHOST               :").append(Build.HOST)
        sb.append("\nTAGS               :").append(Build.TAGS)
        sb.append("\nTYPE               :").append(Build.TYPE)
        sb.append("\nTIME               :").append(Build.TIME)
        sb.append("\nINCREMENTAL        :").append(Build.VERSION.INCREMENTAL)

        sb.append("\n_______ CUPCAKE-3 _______")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            sb.append("\nDISPLAY            :").append(Build.DISPLAY)
        }

        sb.append("\n_______ DONUT-4 _______")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            sb.append("\nSDK_INT            :").append(Build.VERSION.SDK_INT)
            sb.append("\nMANUFACTURER       :").append(Build.MANUFACTURER)
            sb.append("\nBOOTLOADER         :").append(Build.BOOTLOADER)
            sb.append("\nCPU_ABI            :").append(Build.CPU_ABI)
            sb.append("\nCPU_ABI2           :").append(Build.CPU_ABI2)
            sb.append("\nHARDWARE           :").append(Build.HARDWARE)
            sb.append("\nUNKNOWN            :").append(Build.UNKNOWN)
            sb.append("\nCODENAME           :").append(Build.VERSION.CODENAME)
        }

        sb.append("\n_______ GINGERBREAD-9 _______")

        sb.append("\nIMEI               :").append(getImei(context))
        sb.append("\nMacWifi            :").append(getMacaddressWifi(context))
        sb.append("\nMacEthernet        :").append(getMacAddressEthernet())
        sb.append("\nDeviceID           :").append(getDeviceID(context))
        sb.append("\nSeriaNO            :").append(getSerialNO())

        return sb.toString()
    }

    /**
     * 屏幕是否锁定
     *
     * @param context Context
     * @return boolean | true:屏幕已锁;false:屏幕未锁
     */
    fun isScreenLocked(context: Context): Boolean {
        val mKeyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        return mKeyguardManager.inKeyguardRestrictedInputMode()
    }

    /**
     * 检测IMEI是否合法
     *
     * @param str String | IMEI
     * @return boolean | true:合法;false:不合法
     */
    fun checkIMEI(str: String): Boolean {
        return !TextUtils.isEmpty(str) && !str.startsWith("00")
    }

    /**
     * 检测Mac地址是否合法
     *
     * @param str String | mac地址
     * @return boolean | true:合法;false:不合法
     */
    fun checkMacAddress(str: String): Boolean {
        return !TextUtils.isEmpty(str) && !TextUtils.equals(str, "02:00:00:00:00:00")
    }
}