package com.williamlu.widgetlib.banner_card

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.williamlu.widgetlib.R

/**
 * @Author: WilliamLu
 * @Data: 2019/3/25
 * @Description:
 */
class BannerAdapter(context: Context, list: ArrayList<String>) : RecyclerView.Adapter<BannerAdapter.ViewHolder>() {
    private var list: ArrayList<String>? = null
    private var context: Context? = null
    private var onItemClickListener: OnItemClickListener? = null

    init {
        this.list = list
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_banner_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context!!).load(list!!.get(position % list!!.size)).into(holder.imageView)
        holder.imageView.setOnClickListener {
            if (onItemClickListener != null) {
                onItemClickListener!!.onItemClick(position % list!!.size)
            }
        }
    }

    fun setNewData(newData: ArrayList<String>) {
        list!!.clear()
        list!!.addAll(newData)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView

        init {
            imageView = itemView.findViewById(R.id.item_banner_iv) as ImageView
        }
    }
}