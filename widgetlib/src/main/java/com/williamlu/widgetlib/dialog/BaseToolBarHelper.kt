package com.williamlu.widgetlib.dialog

import android.graphics.Color
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.williamlu.widgetlib.R

/**
 * @Author: WilliamLu
 * @Data: 2018/12/4
 * @Description:
 */
class BaseToolBarHelper(baseToolbar: Toolbar) {
    private var mBaseToolbar: Toolbar? = null
    private var mToolbarIbLeft: ImageButton? = null
    private var mToolbarIbRight1: ImageButton? = null
    private var mToolbarIbRight2: ImageButton? = null
    private var mToolbarTvTitle: TextView? = null
    private var mToolbarLlSearch: LinearLayout? = null
    private var mToolbarEtSearchContent: EditText? = null
    private var mToolbarTvSearchConfirm: TextView? = null

    init {
        mBaseToolbar = baseToolbar
        if (mBaseToolbar != null) {
            mToolbarIbLeft = mBaseToolbar!!.findViewById<ImageButton>(R.id.toolbar_ib_left)
            mToolbarIbRight1 = mBaseToolbar!!.findViewById<ImageButton>(R.id.toolbar_ib_right1)
            mToolbarIbRight2 = mBaseToolbar!!.findViewById<ImageButton>(R.id.toolbar_ib_right2)
            mToolbarTvTitle = mBaseToolbar!!.findViewById<TextView>(R.id.toolbar_tv_title)
            mToolbarLlSearch = mBaseToolbar!!.findViewById<LinearLayout>(R.id.toolbar_ll_search)
            mToolbarEtSearchContent = mBaseToolbar!!.findViewById<EditText>(R.id.toolbar_et_search_content)
            mToolbarTvSearchConfirm = mBaseToolbar!!.findViewById<TextView>(R.id.toolbar_tv_search_confirm)
        }
    }

    /*companion object {
        private var INSTANCE: BaseToolBarHelper? = null

        fun getInstance(baseToolbar: Toolbar): BaseToolBarHelper {
            synchronized(BaseToolBarHelper::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = BaseToolBarHelper(baseToolbar)
                }
            }
            return INSTANCE!!
        }
    }*/

    fun showLeftView(): BaseToolBarHelper {
        mToolbarIbLeft!!.visibility = View.VISIBLE
        return this
    }

    fun showRight1View(): BaseToolBarHelper {
        mToolbarIbRight1!!.visibility = View.VISIBLE
        return this
    }

    fun showRight2View(): BaseToolBarHelper {
        mToolbarIbRight2!!.visibility = View.VISIBLE
        return this
    }

    fun showSearchView(): BaseToolBarHelper {
        mToolbarIbRight1!!.visibility = View.GONE
        mToolbarIbRight2!!.visibility = View.GONE
        mToolbarLlSearch!!.visibility = View.VISIBLE
        mToolbarEtSearchContent!!.visibility = View.VISIBLE
        mToolbarTvSearchConfirm!!.visibility = View.VISIBLE
        return this
    }

    fun dismissLeftView(): BaseToolBarHelper {
        mToolbarIbLeft!!.visibility = View.GONE
        return this
    }

    fun dismissRight1View(): BaseToolBarHelper {
        mToolbarIbRight1!!.visibility = View.GONE
        return this
    }

    fun dismissRight2View(): BaseToolBarHelper {
        mToolbarIbRight2!!.visibility = View.GONE
        return this
    }

    fun dismissSearchView(): BaseToolBarHelper {
        mToolbarLlSearch!!.visibility = View.GONE
        mToolbarEtSearchContent!!.visibility = View.GONE
        mToolbarTvSearchConfirm!!.visibility = View.GONE
        return this
    }

    fun dismissAllRightView(): BaseToolBarHelper {
        mToolbarIbRight1!!.visibility = View.GONE
        mToolbarIbRight2!!.visibility = View.GONE
        return this
    }

    fun dismissAllView(): BaseToolBarHelper {
        mToolbarIbLeft!!.visibility = View.GONE
        mToolbarIbRight1!!.visibility = View.GONE
        mToolbarIbRight2!!.visibility = View.GONE
        return this
    }

    fun setBgImg(right1ResId: Int) {
        setBgImg(-1, right1ResId)
    }

    fun setBgImg(leftResId: Int, right1ResId: Int) {
        setBgImg(leftResId, right1ResId, -1)
    }

    fun setBgImg(leftResId: Int, right1ResId: Int, right2ResId: Int): BaseToolBarHelper {
        if (leftResId != -1) {
            mToolbarIbLeft!!.setBackgroundResource(leftResId)
        }
        if (right1ResId != -1) {
            mToolbarIbRight1!!.setBackgroundResource(right1ResId)
        }
        if (right2ResId != -1) {
            mToolbarIbRight2!!.setBackgroundResource(right2ResId)
        }
        return this
    }

    fun setTitleColor(colorId: Int): BaseToolBarHelper {
        mToolbarTvTitle!!.setTextColor(colorId)
        return this
    }

    fun setTitleName(name: String): BaseToolBarHelper {
        mToolbarTvTitle!!.text = name
        return this
    }

    fun getLeftView(): ImageButton {
        return mToolbarIbLeft!!
    }

    fun getRight1View(): ImageButton {
        return mToolbarIbRight1!!
    }

    fun getRight2View(): ImageButton {
        return mToolbarIbRight1!!
    }

    fun getEtSearchContentView(): EditText {
        return mToolbarEtSearchContent!!
    }

    fun getSearchConfirmView(): TextView {
        return mToolbarTvSearchConfirm!!
    }

}