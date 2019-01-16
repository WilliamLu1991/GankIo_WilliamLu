package com.williamlu.toolslib

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

/**
 * @Author: WilliamLu
 * @Data: 2019/1/8
 * @Description:获取资源文件工具类
 */
object GetAssetsUtils {
    /**
     * 获取assets目录下的图片
     *
     * @param context
     * @param fileName
     * @return
     */
    fun getImageFromAssetsFile(context: Context, fileName: String): Bitmap? {
        var image: Bitmap? = null
        var am = context.resources.assets
        try {
            var ips = am.open(fileName)
            image = BitmapFactory.decodeStream(ips)
            ips.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return image
    }

    /**
     * 获取assets目录下的单个文件
     * @param context
     * @param fileName
     * @return
     */
    fun getFilePathFromAssets(context: Context, fileName: String): File {
        val path = "file:///android_asset/$fileName"
        return File(path)
    }

    /**
     * 获取assets目录下的单个文件流String
     * @param context
     * @param fileName
     * @return
     */
    fun getStringFromAssets(context: Context, fileName: String): String {
        val stringBuilder = StringBuilder()
        try {
            val assetManager = context.assets
            val bf = BufferedReader(InputStreamReader(assetManager.open(fileName)))
            var line: String
            while (true) {
                line = bf.readLine() ?: break
                stringBuilder.append(line)
            }
            bf.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return stringBuilder.toString()
    }

    /**
     * 获取assets下文件路径
     * @param context
     * @param fileName
     * @return
     */
    fun getPathFromAssets(context: Context, fileName: String): String {
        return "file:///android_asset/$fileName"
    }

}