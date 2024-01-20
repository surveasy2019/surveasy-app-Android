package com.surveasy.surveasy.presentation.main.survey.fs

import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentFirstSurvey1Binding
import com.surveasy.surveasy.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstSurvey1Fragment :
    BaseFragment<FragmentFirstSurvey1Binding>(R.layout.fragment_first_survey1) {
    private val viewModel: FirstSurveyViewModel by activityViewModels()
    override fun initData() {

    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is FsEvents.NavigateToInput2 -> {
                        findNavController().toInput2()
                    }

                    is FsEvents.NavigateToBack -> findNavController().navigateUp()
                    else -> Unit
                }
            }
        }
    }

    override fun initView() {

        binding.btnNext.setOnClickListener { findNavController().toInput2() }
    }

    private fun NavController.toInput2() {
        navigate(FirstSurvey1FragmentDirections.actionFirstSurvey1FragmentToFirstSurvey2Fragment())
    }
}