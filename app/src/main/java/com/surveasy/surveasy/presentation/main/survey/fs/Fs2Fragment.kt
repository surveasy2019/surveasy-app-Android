package com.surveasy.surveasy.presentation.main.survey.fs

import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentFs2Binding
import com.surveasy.surveasy.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Fs2Fragment : BaseFragment<FragmentFs2Binding>(R.layout.fragment_fs2) {
    private val viewModel: FsViewModel by activityViewModels()
    override fun initData() {

    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is FsEvents.NavigateToDone -> findNavController().toDone()
                    is FsEvents.NavigateToBack -> findNavController().navigateUp()
                    else -> Unit
                }
            }
        }
    }

    override fun initView() {

    }

    private fun NavController.toDone() {
        navigate(Fs2FragmentDirections.actionFs2FragmentToFsDoneFragment())
    }
}