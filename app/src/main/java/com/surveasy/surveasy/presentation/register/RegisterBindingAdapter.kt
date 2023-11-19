package com.surveasy.surveasy.presentation.register

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
        else -> ""
    }

    setHelperTextTextAppearance(R.style.HelperTextStyle)
}