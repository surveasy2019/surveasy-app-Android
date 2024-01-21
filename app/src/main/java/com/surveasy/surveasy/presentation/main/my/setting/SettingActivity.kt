package com.surveasy.surveasy.presentation.main.my.setting

import android.content.Intent
import androidx.activity.viewModels
import com.surveasy.surveasy.databinding.ActivitySettingBinding
import com.surveasy.surveasy.presentation.base.BaseActivity
import com.surveasy.surveasy.presentation.intro.IntroActivity
import com.surveasy.surveasy.presentation.util.showTwoButtonDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity<ActivitySettingBinding>(ActivitySettingBinding::inflate) {
    private val viewModel: SettingViewModel by viewModels()
    override fun initData() {

    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is SettingUiEvents.Logout -> toIntro()
                    is SettingUiEvents.Withdraw -> toWithdraw()
                    is SettingUiEvents.ShowSnackBar -> showSnackBar(it.msg)
                    else -> Unit
                }
            }
        }

    }

    override fun initView() = with(binding) {
        vLogout.setOnClickListener {
            showTwoButtonDialog(
                this@SettingActivity,
                "로그아웃 하시겠습니까?",
                "",
                "로그아웃",
                "닫기",
                { viewModel.logout() }
            ) {}
        }

        vWithdraw.setOnClickListener { viewModel.navigateToWithdraw() }

    }

    private fun toIntro() {
        val intent = Intent(this, IntroActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun toWithdraw() {
        val intent = Intent(this, WithdrawActivity::class.java)
        startActivity(intent)
    }
}