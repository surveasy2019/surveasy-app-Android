package com.surveasy.surveasy.presentation.main.survey

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentSurveyDoneBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import com.surveasy.surveasy.presentation.util.toList
import com.surveasy.surveasy.presentation.util.toMy

class SurveyDoneFragment : BaseFragment<FragmentSurveyDoneBinding>(R.layout.fragment_survey_done) {
    private val viewModel: SurveyViewModel by viewModels()
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
                    is SurveyEvents.NavigateToMy -> findNavController().toMy()
                    is SurveyEvents.NavigateToList -> findNavController().toList()
                    else -> Unit
                }
            }
        }
    }


}
