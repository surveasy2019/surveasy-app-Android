package com.surveasy.surveasy.presentation.bindingadapters

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.surveasy.surveasy.R

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("imgUrl")
fun ImageView.showImg(imgUrl: String?) {
    Glide.with(this.context)
        .load(imgUrl)
        .placeholder(resources.getDrawable(R.drawable.ic_image_loading, null))
        .error(resources.getDrawable(R.drawable.ic_image_error, null))
        .fallback(resources.getDrawable(R.drawable.ic_image_error, null))
        .into(this)
}