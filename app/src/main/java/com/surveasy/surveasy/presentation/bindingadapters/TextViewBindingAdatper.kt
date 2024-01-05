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

@BindingAdapter("englishTitle")
fun TextView.englishTitle(english: Boolean) {
    text = if (english) "영어 설문에 참여합니다." else "영어 설문에 참여하지 않습니다."
}

@BindingAdapter("respondedCnt", "totalCnt")
fun TextView.participatedTitle(responded: Int, total: Int) {
    text = "${responded}/${total}"
}