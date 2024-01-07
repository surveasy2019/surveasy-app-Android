package com.surveasy.surveasy.presentation.main.my.history

import androidx.fragment.app.activityViewModels
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentHistoryDetailBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryDetailFragment :
    BaseFragment<FragmentHistoryDetailBinding>(R.layout.fragment_history_detail) {
    private val viewModel: HistoryViewModel by activityViewModels()

    override fun initView() {
        binding.vm = viewModel
    }

    override fun initData() {
        viewModel.getHistoryDetail()
    }

    override fun initEventObserver() {

    }
}