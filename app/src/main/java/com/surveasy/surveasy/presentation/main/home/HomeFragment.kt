package com.surveasy.surveasy.presentation.main.home

import android.content.Intent
import androidx.fragment.app.viewModels
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentHomeBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import com.surveasy.surveasy.presentation.main.survey.SurveyActivity
import com.surveasy.surveasy.presentation.util.IntentId
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels()
    private val adapter = HomeListAdapter {
        viewModel.navigateToSurveyDetail(it)
    }

    override fun initData() {
        viewModel.queryPanelInfo()
        viewModel.listHomeSurvey()
    }

    override fun initView() = with(binding) {
        vm = viewModel
        rvHomeList.adapter = adapter
    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is HomeEvents.ClickSurveyItem -> navigateToSurveyDetail(it.id)
                    is HomeEvents.ShowSnackBar -> showSnackBar(it.msg)
                    else -> Unit
                }
            }
        }
    }

    private fun navigateToSurveyDetail(id: Int) {
        val intent = Intent(context, SurveyActivity::class.java)
        intent.putExtra(IntentId.SURVEY_ID, id)
        startActivity(intent)
    }
}