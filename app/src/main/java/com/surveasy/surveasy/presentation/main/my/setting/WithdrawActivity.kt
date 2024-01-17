package com.surveasy.surveasy.presentation.main.my.setting

import android.content.Intent
import com.surveasy.surveasy.databinding.ActivityWithdrawBinding
import com.surveasy.surveasy.presentation.base.BaseActivity
import com.surveasy.surveasy.presentation.intro.IntroActivity
import com.surveasy.surveasy.presentation.util.showTwoButtonDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WithdrawActivity : BaseActivity<ActivityWithdrawBinding>(ActivityWithdrawBinding::inflate) {
    override fun initData() {

    }

    override fun initEventObserver() {

    }

    override fun initView() {
        binding.btnWithdraw.setOnClickListener {
            showTwoButtonDialog(
                this,
                "탈퇴 하시겠습니까?",
                "회원 탈퇴 시 패널 정보가 모두 사라집니다.",
                "탈퇴하기",
                "닫기"
            ) {
                showToastMessage("탈퇴")
            }
        }
    }

    private fun toIntro() {
        val intent = Intent(this, IntroActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}