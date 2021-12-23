package com.example.common

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.common.R

fun ImageView.loadImagesWithGlideExt(url: String) {
    Glide.with(this.context)
        .load(url)
//        .centerCrop()
        .error(R.drawable.error)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
//        .placeholder(android.R.drawable.zoom_plate)
        .into(this)
}