package com.surveasy.surveasy.presentation.main.survey

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentSurveyDoneBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import com.surveasy.surveasy.presentation.util.toList
import com.surveasy.surveasy.presentation.util.toMy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveyDoneFragment : BaseFragment<FragmentSurveyDoneBinding>(R.layout.fragment_survey_done) {
    private val viewModel: SurveyViewModel by activityViewModels()
    override fun initData() {

    }

    override fun initView() = with(binding) {
        vm = viewModel
        requireActivity().onBackPressedDispatcher.addCallback(object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() = viewModel.navigateToList()
        })
    }

    override fun initEventObserver() = Unit


}
