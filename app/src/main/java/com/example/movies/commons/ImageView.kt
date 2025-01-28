package com.example.movies.commons

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.movies.R

internal fun loadImage(context: Context, imageUrl: String?, imageView: ImageView) {
    Glide
        .with(context)
        .load(imageUrl)
        .error(R.drawable.image_placeholder)
        .into(imageView)
}