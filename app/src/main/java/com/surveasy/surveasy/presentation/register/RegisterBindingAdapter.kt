package com.surveasy.surveasy.presentation.register

import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.surveasy.surveasy.R

@BindingAdapter("set_helper_text")
fun TextInputLayout.nameHelperText(state: InputState) {
    helperText = when (state.helperText) {
        HelperText.NAME_INVALID -> "최소 2자 이상 입력해주세요."
        HelperText.EMAIL_INVALID -> "이메일 형식이 맞지 않습니다."
        HelperText.PW_INVALID -> "8자 이상 입력해주세요."
        HelperText.PW_CHECK_INVALID -> "비밀번호가 일치하지 않습니다."
        HelperText.PHONE_INVALID -> "숫자만 입력해주세요."
        HelperText.ACCOUNT_INVALID -> "숫자만 입력해주세요."
        HelperText.OWNER_INVALID -> "최소 2자 이상 입력해주세요."
        else -> ""
    }

    setHelperTextTextAppearance(R.style.HelperTextStyle)
}

@BindingAdapter("set_next_enable")
fun AppCompatButton.setNextEnable(uiState: RegisterUiState) = with(uiState) {
//    isEnabled =
//        nameState.isValid && emailState.isValid && pwState.isValid && pwCheckState.isValid && phoneState.isValid && birthState.isValid && inflowState.isValid
}

@BindingAdapter("set_done_enable")
fun AppCompatButton.setDoneEnable(uiState: RegisterUiState) = with(uiState) {
    // isEnabled = bankState.isValid && accountState.isValid && accountOwnerState.isValid
}