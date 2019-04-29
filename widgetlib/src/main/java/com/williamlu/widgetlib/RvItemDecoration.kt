package com.williamlu.widgetlib

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * @Author: WilliamLu
 * @Data: 2019/4/2
 * @Description:
 */
class RvItemDecoration : RecyclerView.ItemDecoration {
    private var spaceLeft: Int
    private var spaceTop: Int
    private var spaceRight: Int
    private var spaceBottom: Int
    private var isHorizontal: Boolean = false

    constructor(space: Int) {
        this.spaceLeft = 0
        this.spaceTop = space
        this.spaceRight = 0
        this.spaceBottom = space
    }

    constructor(spaceTop: Int, spaceBottom: Int) {
        this.spaceLeft = 0
        this.spaceTop = spaceTop
        this.spaceRight = 0
        this.spaceBottom = spaceBottom
    }

    constructor(spaceLeft: Int, spaceRight: Int, isHorizontal: Boolean) {
        this.spaceLeft = spaceLeft
        this.spaceTop = 0
        this.spaceRight = spaceRight
        this.spaceBottom = 0
        this.isHorizontal = isHorizontal
    }

    constructor(spaceLeft: Int, spaceTop: Int, spaceRight: Int, spaceBottom: Int) {
        this.spaceLeft = spaceLeft
        this.spaceTop = spaceTop
        this.spaceRight = spaceRight
        this.spaceBottom = spaceBottom
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position

        if (isHorizontal) {
            if (position == 0) {
                outRect.left = spaceLeft
            }
            outRect.right = spaceRight
        } else {
            if (position == 0) {
                outRect.top = spaceTop
            }
            outRect.left = spaceLeft
            outRect.right = spaceRight
            outRect.bottom = spaceBottom
        }

    }
}