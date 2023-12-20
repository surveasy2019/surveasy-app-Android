package com.surveasy.surveasy.presentation.main.home

import android.util.Log
import androidx.fragment.app.viewModels
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentHomeBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel : HomeViewModel by viewModels()
    override fun initData() {
        viewModel.queryPanelInfo()
    }

    override fun initView() {

    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.uiState.collectLatest {
                Log.d("TEST", "$it")
            }
        }
    }
}