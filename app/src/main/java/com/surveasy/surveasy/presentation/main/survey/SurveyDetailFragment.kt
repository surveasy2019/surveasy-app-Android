package com.surveasy.surveasy.presentation.main.survey

import androidx.activity.OnBackPressedCallback
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
        requireActivity().onBackPressedDispatcher.addCallback(object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() = viewModel.navigateToList()
        })
    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect { event ->
                when (event) {
                    is SurveyEvents.NavigateToSurvey -> findNavController().toSurvey()
                    is SurveyEvents.ShowSnackBar -> showSnackBar(event.msg, event.btn)
                    else -> Unit
                }
            }
        }
    }

    private fun NavController.toSurvey() {
        navigate(SurveyDetailFragmentDirections.actionSurveyDetailFragmentToSurveyFragment())
    }
}