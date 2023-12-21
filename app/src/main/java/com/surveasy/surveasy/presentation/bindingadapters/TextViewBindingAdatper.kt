package com.surveasy.surveasy.presentation.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("nameTitle")
fun TextView.nameTitle(name: String) {
    text = "${name}님"
}

@BindingAdapter("countTitle")
fun TextView.countTitle(count: Int) {
    text = "${count}개"
}

@BindingAdapter("rewardTitle")
fun TextView.rewardTitle(reward: Int) {
    text = "${reward}개"
}