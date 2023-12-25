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
        initEventObserver()
        binding.vm = viewModel
    }

    private fun initData() {
        viewModel.queryPanelDetailInfo()
    }

    private fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is MyEditUiEvents.DoneEdit -> {
                        showToastMessage("수정이 완료되었습니다.")
                        initData()
                    }
                }
            }
        }
    }
}