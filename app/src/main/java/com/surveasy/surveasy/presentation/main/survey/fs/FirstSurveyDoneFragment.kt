package com.surveasy.surveasy.presentation.main.survey.fs

import androidx.fragment.app.activityViewModels
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentFirstSurveyDoneBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstSurveyDoneFragment :
    BaseFragment<FragmentFirstSurveyDoneBinding>(R.layout.fragment_first_survey_done) {
    private val viewModel: FirstSurveyViewModel by activityViewModels()
    override fun initData() {

    }

    override fun initEventObserver() {

    }

    override fun initView() {

    }
}