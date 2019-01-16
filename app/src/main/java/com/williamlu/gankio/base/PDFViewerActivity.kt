package com.williamlu.gankio.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.williamlu.gankio.R
import kotlinx.android.synthetic.main.activity_pdfviewer.*

class PDFViewerActivity : GankIoBaseActivity() {
    private var mFileName: String? = null
    private var mTitle: String? = null

    companion object {
        fun showClass(context: Context, title: String, fileName: String) {
            val intent = Intent(context, PDFViewerActivity::class.java)
            intent.putExtra("fileName", fileName)
            intent.putExtra("title", title)
            context.startActivity(intent)
        }
    }

    override fun onInitParams(bundle: Bundle) {
        super.onInitParams(bundle)
        mFileName = bundle.getString("fileName")
        mTitle = bundle.getString("title")
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_pdfviewer
    }

    override fun initPresenter() {
    }

    override fun initView() {
        mBaseToolBarHelper!!.setTitleName(mTitle!!).showLeftView().setBgImg(R.drawable.lib_ic_back)

        if (mFileName!!.endsWith("pdf")) {
            pdf_view.fromAsset(mFileName).load()
        } else {
            showEmptyDataView()
        }
    }

    override fun initListener() {
        mBaseToolBarHelper!!.getLeftView().setOnClickListener {
            finish()
        }
    }

}
