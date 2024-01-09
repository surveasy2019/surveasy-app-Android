package com.surveasy.surveasy.presentation.main.my.history

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentHistoryBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>(R.layout.fragment_history) {
    private val viewModel: HistoryViewModel by activityViewModels()
    private val adapter = HistoryAdapter {
        viewModel.navigateToDetail(it)
    }

    override fun initView() {
        initTabListener()
        bind {
            vm = viewModel
            rvHistory.adapter = adapter
        }
    }

    override fun initData() {
        viewModel.listHistory(true)
    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is HistoryEvents.NavigateToDetail -> navigateToDetail()
                    else -> Unit
                }
            }
        }
    }

    private fun initTabListener() {
        binding.tbHistory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> viewModel.listHistory(true)
                    1 -> viewModel.listHistory(false)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
        })
    }

    private fun navigateToDetail() = findNavController().navigate(
        HistoryFragmentDirections.actionHistoryFragmentToHistoryDetailFragment()
    )
}