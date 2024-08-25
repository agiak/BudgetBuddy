package com.example.common.myutils

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import timber.log.Timber

fun ImageView.load(drawableId: Int, placeholder: Int?= null, error: Int? = null) {
    Glide.with(this.context)
        .load(drawableId)
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
        }
        .into(this)
}

fun ImageView.load(url: String, placeholder: Int? = null, error: Int? = null) {
    Glide.with(this.context)
        .load(url)
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
        }
        .into(this)
}

fun ImageView.loadCircle(drawableId: Int, placeholder: Int? = null, error: Int? = null) {
    Glide.with(this.context)
        .load(drawableId)
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
        }
        .transform(CircleCrop()) // Add this line for circle corners
        .into(this)
}

fun ImageView.loadCircle(url: String, placeholder: Int? = null, error: Int? = null) {
    Glide.with(this.context)
        .load(url)
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
        }
        .transform(CircleCrop()) // Add this line for circle corners
        .into(this)
}

fun ImageView.loadCircle(uri: Uri, placeholder: Int? = null, error: Int? = null) {
    Glide.with(this.context)
        .load(uri)
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
        }
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                Timber.e("This error on Glide")
                e?.printStackTrace()
                return true
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }
        })
        .transform(CircleCrop()) // Add this line for circle corners
        .into(this)
}