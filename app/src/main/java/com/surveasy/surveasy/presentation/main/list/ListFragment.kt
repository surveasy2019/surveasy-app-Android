package com.surveasy.surveasy.presentation.main.list

import android.content.Intent
import androidx.fragment.app.viewModels
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentListBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import com.surveasy.surveasy.presentation.main.survey.SurveyActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>(R.layout.fragment_list) {
    private val viewModel: ListViewModel by viewModels()
    private val adapter = SurveyListAdapter {
        viewModel.navigateToSurveyDetail(it)
    }

    override fun initView() {
        bind {
            vm = viewModel
            rvList.adapter = adapter
        }
    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is ListEvents.ClickSurveyItem -> {
                        startActivity(Intent(context, SurveyActivity::class.java))
                    }
                }
            }
        }
    }

    override fun initData() {
        viewModel.listSurvey()
    }
}