package com.surveasy.surveasy.presentation.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.surveasy.surveasy.R
import com.surveasy.surveasy.presentation.intro.HelperText
import com.surveasy.surveasy.presentation.intro.InputState

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
    text = "${reward}원"
}

@BindingAdapter("englishTitle")
fun TextView.englishTitle(english: Boolean) {
    text = if (english) "영어 설문에 참여합니다." else "영어 설문에 참여하지 않습니다."
}

@BindingAdapter("respondedCnt", "totalCnt")
fun TextView.participatedTitle(responded: Int, total: Int) {
    text = "${responded}/${total}명"
}

@BindingAdapter("getReward")
fun TextView.getReward(reward: Int) {
    text = "${reward}원 받기"
}

@BindingAdapter("setHelperText")
fun TextInputLayout.nameHelperText(state: InputState) {
    helperText = when (state.helperText) {
        HelperText.ACCOUNT_INVALID -> "숫자만 입력해주세요."
        HelperText.OWNER_INVALID -> "최소 2자 이상 입력해주세요."
        HelperText.INFLOW_ETC_INVALID -> "최소 2자 이상 입력해주세요."
        else -> ""
    }

    setHelperTextTextAppearance(R.style.HelperTextStyle)
}