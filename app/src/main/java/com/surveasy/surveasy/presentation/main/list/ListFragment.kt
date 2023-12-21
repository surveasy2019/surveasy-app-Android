package com.surveasy.surveasy.presentation.main.list

import android.content.Intent
import androidx.fragment.app.viewModels
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentListBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import com.surveasy.surveasy.presentation.main.survey.SurveyActivity

class ListFragment : BaseFragment<FragmentListBinding>(R.layout.fragment_list) {
    private val viewModel: ListViewModel by viewModels()
    override fun initView() {
        bind {
            tvListTitle.setOnClickListener {
                viewModel.navigateToSurveyDetail()
            }
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

    }
}