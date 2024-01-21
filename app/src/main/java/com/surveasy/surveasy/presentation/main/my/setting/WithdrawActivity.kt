package com.surveasy.surveasy.presentation.main.my.setting

import android.content.Intent
import androidx.activity.viewModels
import com.surveasy.surveasy.databinding.ActivityWithdrawBinding
import com.surveasy.surveasy.presentation.base.BaseActivity
import com.surveasy.surveasy.presentation.intro.IntroActivity
import com.surveasy.surveasy.presentation.util.showTwoButtonDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WithdrawActivity : BaseActivity<ActivityWithdrawBinding>(ActivityWithdrawBinding::inflate) {
    private val viewModel: WithdrawViewModel by viewModels()
    override fun initData() {

    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is WithdrawEvents.Withdraw -> toIntro()
                    is WithdrawEvents.ShowSnackBar -> showSnackBar(it.msg)
                    is WithdrawEvents.NavigateToBack -> finish()
                    else -> Unit
                }
            }
        }
    }

    override fun initView() {
        initCheckbox()
        binding.btnWithdraw.setOnClickListener {
            showTwoButtonDialog(
                this,
                "탈퇴 하시겠습니까?",
                "회원 탈퇴 시 패널 정보가 모두 사라집니다.",
                "탈퇴하기",
                "닫기",
                { viewModel.withdraw() }
            ) { }
        }
    }

    private fun initCheckbox() = with(binding) {
        cb1.setOnCheckedChangeListener { buttonView, _ ->
            viewModel.setReason1(buttonView.isChecked)
        }
        cb2.setOnCheckedChangeListener { buttonView, _ ->
            viewModel.setReason2(buttonView.isChecked)
        }
        cb3.setOnCheckedChangeListener { buttonView, _ ->
            viewModel.setReason3(buttonView.isChecked)
        }
        cb4.setOnCheckedChangeListener { buttonView, _ ->
            viewModel.setReason4(buttonView.isChecked)
        }
        cb5.setOnCheckedChangeListener { buttonView, _ ->
            viewModel.setReason5(buttonView.isChecked)
        }

    }

    private fun toIntro() {
        val intent = Intent(this, IntroActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}