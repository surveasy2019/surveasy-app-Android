package com.surveasy.surveasy.presentation.main.survey.fs

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentFsDoneBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FsDoneFragment : BaseFragment<FragmentFsDoneBinding>(R.layout.fragment_fs_done) {
    private val viewModel: FsViewModel by activityViewModels()
    override fun initData() {

    }

    override fun initEventObserver() {

    }

    override fun initView() {
        requireActivity().onBackPressedDispatcher.addCallback(object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() = viewModel.navigateToList()
        })
    }
}