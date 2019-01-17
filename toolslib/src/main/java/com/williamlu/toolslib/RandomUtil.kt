package com.williamlu.toolslib

import android.content.Context
import java.util.*

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 生成随机数工具类
 */
object RandomUtil {

    /**
     * 创建指定数量的随机字符串
     *
     * @param numberFlag 是否是数字
     * @param length
     * @return
     */
    fun createRandom(numberFlag: Boolean, length: Int): String {
        var retStr = ""
        val strTable = if (numberFlag) "1234567890" else "1234567890abcdefghijkmnpqrstuvwxyz"
        val len = strTable.length
        var bDone = true
        do {
            retStr = ""
            var count = 0
            for (i in 0 until length) {
                val dblR = Math.random() * len
                val intR = Math.floor(dblR).toInt()
                val c = strTable[intR]
                if ('0' <= c && c <= '9') {
                    count++
                }
                retStr += strTable[intR]
            }
            if (count >= 2) {
                bDone = false
            }
        } while (bDone)

        return retStr
    }


    /**
     * 创建随机颜色
     */
    fun getRandomColorStr(): String {
        var colorStr = ""
        var tempStr = StringBuilder()
        var c = arrayOf("a", "b", "c", "d", "e", "f", "0", "1", "3", "4", "5", "6", "7", "8", "9")

        var random = Random()

        for (i in 1..6) {
            tempStr.append(c[random.nextInt(c.size)])
        }

        colorStr = "#$tempStr"

        return colorStr

    }
}