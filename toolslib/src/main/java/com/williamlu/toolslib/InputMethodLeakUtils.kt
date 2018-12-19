package com.williamlu.toolslib

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.KITKAT
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.lang.reflect.Field

/**
 * @Author: WilliamLu
 * @Data: 2018/12/19
 * @Description: InputMethodManager造成的内存泄漏问题解决方法（在泄漏类的onDestroy()方法中调用）
 */
object InputMethodLeakUtils {

    fun fixInputMethodManagerLeak(context: Context) {
        if (SDK_INT < KITKAT || SDK_INT > 22) {
            return
        }
        if (context == null) {
            return
        }
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val arr = arrayOf("mCurRootView", "mServedView", "mNextServedView")
        var f: Field?
        var obj_get: Any?
        for (i in arr.indices) {
            val param = arr[i]
            try {
                f = imm.javaClass.getDeclaredField(param)
                if (f == null) {
                    return
                }
                f.isAccessible = true
                obj_get = f.get(imm)
                if (obj_get != null && obj_get is View) {
                    val v_get = obj_get as View?
                    if (v_get!!.context === context) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null) // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        break
                    }
                }
            } catch (t: Throwable) {
                t.printStackTrace()
            }

        }
    }
}