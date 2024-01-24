package com.surveasy.surveasy.presentation.bindingadapters

import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import com.surveasy.surveasy.presentation.intro.RegisterUiState

@BindingAdapter("rewardBtn")
fun AppCompatButton.rewardBtn(reward: Int) {
    text = "${reward}원 받으러 가기"
}

@BindingAdapter("setDoneEnable", "needEtc")
fun AppCompatButton.setDoneEnable(uiState: RegisterUiState?, needEtc: Boolean) {
    uiState ?: return
    with(uiState) {
        isEnabled =
            inflowState.isValid && bankState.isValid && accountState.isValid && accountOwnerState.isValid && (!needEtc || inflowEtcState.isValid)
    }
}