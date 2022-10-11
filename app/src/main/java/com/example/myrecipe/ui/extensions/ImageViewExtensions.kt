package com.example.myrecipe.ui.extensions

import android.widget.ImageView
import coil.load
import com.example.myrecipe.R

fun ImageView.tryLoadImage(url: String? = null) {
    load(url) {
        fallback(R.drawable.fallback_image)
        error(R.drawable.error_image)
        placeholder(R.drawable.fallback_image)
    }
}