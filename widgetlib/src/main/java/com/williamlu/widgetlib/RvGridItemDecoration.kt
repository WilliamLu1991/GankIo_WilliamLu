package com.williamlu.widgetlib

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * @Author: WilliamLu
 * @Data: 2019/4/2
 * @Description:
 */
class RvGridItemDecoration : RecyclerView.ItemDecoration {
    private var spanCount: Int
    private var space: Int = -1
    private var includeEdge: Boolean = false

    constructor(spanCount: Int, space: Int, includeEdge: Boolean) {
        this.spanCount = spanCount
        this.space = space
        this.includeEdge = includeEdge
    }
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        if (includeEdge) {
            outRect.left = space - column * space / spanCount // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * space / spanCount // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = space
            }
            outRect.bottom = space // item bottom
        } else {
            outRect.left = column * space / spanCount // column * ((1f / spanCount) * spacing)
            outRect.right = space - (column + 1) * space / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = space // item top
            }
        }

    }
}