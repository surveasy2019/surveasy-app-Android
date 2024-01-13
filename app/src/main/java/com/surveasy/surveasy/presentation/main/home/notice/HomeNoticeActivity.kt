package com.surveasy.surveasy.presentation.main.home.notice

import com.surveasy.surveasy.databinding.ActivityHomeNoticeBinding
import com.surveasy.surveasy.presentation.base.BaseActivity

class HomeNoticeActivity :
    BaseActivity<ActivityHomeNoticeBinding>(ActivityHomeNoticeBinding::inflate) {
    override fun initData() {

    }

    override fun initEventObserver() {

    }

    override fun initView() = with(binding) {
        ivBack.setOnClickListener { finish() }
    }
}