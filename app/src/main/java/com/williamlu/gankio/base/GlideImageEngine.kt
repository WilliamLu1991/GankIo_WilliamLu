package com.williamlu.gankio.base

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Priority
import com.zhihu.matisse.engine.ImageEngine


/**
 * @Author: WilliamLu
 * @Data: 2019/1/3
 * @Description:
 */
class GlideImageEngine : ImageEngine {

    override fun loadThumbnail(context: Context, resize: Int, placeholder: Drawable, imageView: ImageView, uri: Uri) {
        GlideApp.with(context)
            .asBitmap()
            .load(uri)
            .placeholder(placeholder)
            .override(resize, resize)
            .centerCrop()
            .into(imageView)
    }

    override fun loadGifThumbnail(
        context: Context, resize: Int, placeholder: Drawable, imageView: ImageView,
        uri: Uri
    ) {
        GlideApp.with(context)
            .asBitmap()
            .load(uri)
            .placeholder(placeholder)
            .override(resize, resize)
            .centerCrop()
            .into(imageView)
    }

    override fun loadImage(context: Context, resizeX: Int, resizeY: Int, imageView: ImageView, uri: Uri) {
        GlideApp.with(context)
            .load(uri)
            .override(resizeX, resizeY)
            .priority(Priority.HIGH)
            .into(imageView)
    }

    override fun loadGifImage(context: Context, resizeX: Int, resizeY: Int, imageView: ImageView, uri: Uri) {
        GlideApp.with(context)
            .asGif()
            .load(uri)
            .override(resizeX, resizeY)
            .priority(Priority.HIGH)
            .into(imageView)
    }

    override fun supportAnimatedGif(): Boolean {
        return true
    }
}