package com.surveasy.surveasy.presentation.bindingadapters

import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.surveasy.surveasy.R
import com.surveasy.surveasy.presentation.intro.HelperText
import com.surveasy.surveasy.presentation.intro.InputState
import com.surveasy.surveasy.presentation.intro.RegisterUiState

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


@BindingAdapter("setDoneEnable", "needEtc")
fun AppCompatButton.setDoneEnable(uiState: RegisterUiState?, needEtc: Boolean) {
    uiState ?: return
    with(uiState) {
        isEnabled =
            inflowState.isValid && bankState.isValid && accountState.isValid && accountOwnerState.isValid && (!needEtc || inflowEtcState.isValid)
    }
}