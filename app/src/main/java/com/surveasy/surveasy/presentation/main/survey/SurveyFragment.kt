package com.surveasy.surveasy.presentation.main.survey

import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentSurveyBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveyFragment : BaseFragment<FragmentSurveyBinding>(R.layout.fragment_survey) {
    private val viewModel: SurveyViewModel by activityViewModels()
    override fun initData() {

    }

    override fun initView() {
        bind {
            vm = viewModel
        }
    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect { event ->
                when (event) {
                    is SurveyEvents.NavigateToProof -> findNavController().toSurveyProof()
                    else -> Unit
                }
            }
        }
    }

    private fun NavController.toSurveyProof() {
        navigate(SurveyFragmentDirections.actionSurveyFragmentToSurveyProofFragment())
    }
}