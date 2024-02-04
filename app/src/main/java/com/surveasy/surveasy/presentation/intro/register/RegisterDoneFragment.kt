package com.surveasy.surveasy.presentation.intro.register

import android.content.Intent
import androidx.activity.OnBackPressedCallback
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentRegisterDoneBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import com.surveasy.surveasy.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterDoneFragment :
    BaseFragment<FragmentRegisterDoneBinding>(R.layout.fragment_register_done) {
    override fun initData() = Unit

    override fun initEventObserver() = Unit

    override fun initView() {
        binding.btnStart.setOnClickListener {
            navigateToMain()
        }
        requireActivity().onBackPressedDispatcher.addCallback(object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() = Unit
        })
    }

    private fun navigateToMain() {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}