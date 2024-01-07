package com.surveasy.surveasy.presentation.main.my.history

import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentHistoryBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>(R.layout.fragment_history){
    private val viewModel: HistoryViewModel by viewModels()
    override fun initView() {
        initTabListener()
        bind {
            vm = viewModel
        }
    }

    override fun initData() {
        viewModel.listHistory(BEFORE)
    }

    override fun initEventObserver() {

    }

    private fun initTabListener() {
        binding.tbHistory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> viewModel.listHistory(BEFORE)
                    1 -> viewModel.listHistory(AFTER)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
        })
    }

    companion object {
        const val BEFORE = "before"
        const val AFTER = "after"
    }
}