package com.williamlu.toolslib

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.util.*

/**
 * @Author: WilliamLu
 * @Data: 2019/1/9
 * @Description:
 */
object BitmapUtil {
    /**
     * 图片转成string
     *
     * @param bitmap
     * @return
     */
    @SuppressLint("NewApi")
    fun convertBitmapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream() // outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val bytes = baos.toByteArray() // 转为byte数组
        return Base64.getEncoder().encodeToString(bytes)

    }

    /**
     * string转成bitmap
     *
     * @param str
     */
    @SuppressLint("NewApi")
    fun convertStringToBitmap(str: String): Bitmap? {
        // OutputStream out;
        var bitmap: Bitmap
        try {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            val bytes = Base64.getDecoder().decode(str)
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap
        } catch (e: Exception) {
            return null
        }

    }

    /** uri转化为bitmap */
    private fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
        try {
            // 读取uri所在的图片
            return MediaStore.Images.Media.getBitmap(
                context.contentResolver, uri
            )
        } catch (e: Exception) {
            return null
        }

    }

    /** 根据文件路径转换为bitmap */
    fun getBitemapFromFile(fileName: String): Bitmap? {
        try {
            val opt = BitmapFactory.Options()
            opt.inPreferredConfig = Bitmap.Config.RGB_565
            opt.inPurgeable = true
            opt.inInputShareable = true
            opt.inSampleSize = 8
            return BitmapFactory.decodeFile(fileName, opt)

        } catch (ex: Exception) {
            return null
        }

    }

    /** 根据path得到适当的缩略图 */
    fun getCompressBitmapFromPath(filePath: String): Bitmap? {
        var bitmap: Bitmap? = null
        val opts = BitmapFactory.Options()
        opts.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, opts)
        opts.inSampleSize = compressBitmapSize(opts, -1, 128 * 128)
        opts.inJustDecodeBounds = false

        try {
            bitmap = BitmapFactory.decodeFile(filePath, opts)
        } catch (e: Exception) {
            return null
        }

        return bitmap
    }

    private fun compressBitmapSize(options: BitmapFactory.Options, minSideLength: Int, maxNumOfPixels: Int): Int {
        val w = options.outWidth.toDouble()
        val h = options.outHeight.toDouble()
        val lowerBound = if (maxNumOfPixels == -1)
            1
        else
            Math.ceil(
                Math
                    .sqrt(w * h / maxNumOfPixels)
            ).toInt()
        val upperBound = if (minSideLength == -1)
            128
        else
            Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength)
            ).toInt()
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound
        }
        return if (maxNumOfPixels == -1 && minSideLength == -1) {
            1
        } else if (minSideLength == -1) {
            lowerBound
        } else {
            upperBound
        }
    }

}