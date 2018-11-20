package com.williamlu.datalib.base

import com.orhanobut.logger.Logger
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException

/*
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 
 */
class LogInterceptor : Interceptor {

    private val UTF8 = Charset.forName("UTF-8")

    private fun bodyToString(request: Request): String {
        val copy = request.newBuilder().build()
        if (copy.body() == null) {
            return ""
        }
        try {
            val buffer = Buffer()
            copy.body()!!.writeTo(buffer)
            return buffer.readUtf8()
        } catch (e: IOException) {
            e.printStackTrace()
            return ""
        }

    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        Logger.d(String.format("Sending request==> %s on %s", request.url(), bodyToString(request)))

        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            throw e
        }

        val responseBody = response.body()
        val source = responseBody!!.source()
        source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
        val buffer = source.buffer()
        var charset: Charset? = UTF8
        val contentType = responseBody.contentType()
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8)
            } catch (e: UnsupportedCharsetException) {
                return response
            }

        }

        Logger.d(String.format("Received response==> for %s in %s", response.request().url(), buffer.clone().readString(charset!!)))

        return response
    }
}