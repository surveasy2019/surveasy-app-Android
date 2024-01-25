package com.surveasy.surveasy.presentation.main.my.history

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentHistoryBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import com.surveasy.surveasy.presentation.main.my.edit.EditActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>(R.layout.fragment_history) {
    private val viewModel: HistoryViewModel by activityViewModels()
    private val adapter = HistoryAdapter {
        viewModel.navigateToDetail(it)
    }

    override fun initView() = with(binding) {
        initTabListener()
        vm = viewModel
        rvHistory.adapter = adapter
        rvHistory.animation = null
        rvHistory.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val scrollBottom = !rvHistory.canScrollVertically(1)
                val isLastPage = viewModel.mainUiState.value.lastPage
                val isNotLoading = !viewModel.mainUiState.value.isLoading


                if (scrollBottom && !isLastPage && isNotLoading) {
                    viewModel.loadNextPage()
                }
            }
        })
    }

    override fun initData() {
        viewModel.queryAccountInfo()
        viewModel.listHistory(true)
    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is HistoryEvents.NavigateToDetail -> toDetail()
                    is HistoryEvents.NavigateToInfoEdit -> toInfoEdit()
                    is HistoryEvents.ShowSnackBar -> showSnackBar(it.msg)
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

    private fun toDetail() = findNavController().navigate(
        HistoryFragmentDirections.actionHistoryFragmentToHistoryDetailFragment()
    )

    private fun toInfoEdit() {
        val intent = Intent(requireContext(), EditActivity::class.java)
        startActivity(intent)
    }
}