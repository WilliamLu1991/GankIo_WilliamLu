package com.williamlu.gankio.home.view

import android.content.Context
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.view.ViewGroup
import android.widget.FrameLayout
import com.williamlu.gankio.R

/**
 * @Author: WilliamLu
 * @Data: 2018/12/13
 * @Description:
 */
class FullSheetDialog(context: Context) : BottomSheetDialog(context) {
    override fun show() {
        super.show()
        window.apply {
            var layout = decorView.findViewById<FrameLayout>(R.id.design_bottom_sheet)
            var orginLayoutParams = layout.layoutParams
            orginLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            layout.layoutParams = orginLayoutParams
            val mDialogBehavior = BottomSheetBehavior.from(layout)
            mDialogBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
        }
    }
}
