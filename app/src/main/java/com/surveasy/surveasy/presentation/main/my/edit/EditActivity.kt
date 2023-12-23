package com.surveasy.surveasy.presentation.main.my.edit

import android.os.Bundle
import androidx.activity.viewModels
import com.surveasy.surveasy.databinding.ActivityEditBinding
import com.surveasy.surveasy.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditActivity : BaseActivity<ActivityEditBinding>(ActivityEditBinding::inflate) {
    private val viewModel: MyEditViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        binding.vm = viewModel
    }

    private fun initData() {
        viewModel.queryPanelDetailInfo()
    }
}