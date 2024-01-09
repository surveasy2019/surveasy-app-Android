package com.surveasy.surveasy.presentation.main.survey

import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentSurveyDetailBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveyDetailFragment :
    BaseFragment<FragmentSurveyDetailBinding>(R.layout.fragment_survey_detail) {
    private val viewModel: SurveyViewModel by activityViewModels()
    override fun initData() {
        viewModel.querySurveyDetail()
    }

    override fun initView() = with(binding) {
        vm = viewModel
    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect { event ->
                when (event) {
                    is SurveyEvents.NavigateToSurvey -> findNavController().toSurvey()
                    else -> Unit
                }
            }
        }
    }

    private fun NavController.toSurvey() {
        navigate(SurveyDetailFragmentDirections.actionSurveyDetailFragmentToSurveyFragment())
    }
}