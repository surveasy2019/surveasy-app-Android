package com.surveasy.surveasy.presentation.main.my.contact

import android.content.ClipData
import android.content.ClipboardManager
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivityContactBinding
import com.surveasy.surveasy.presentation.base.BaseActivity

class ContactActivity : BaseActivity<ActivityContactBinding>(ActivityContactBinding::inflate) {
    override fun initData() {

    }

    override fun initEventObserver() {

    }

    override fun initView() = with(binding) {
        btnCopy.setOnClickListener {
            val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", resources.getText(R.string.my_email))
            clipboardManager.setPrimaryClip(clipData)

            showToastMessage("이메일 주소가 복사되었습니다.")
        }
    }
}