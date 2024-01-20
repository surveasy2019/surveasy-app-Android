package com.surveasy.surveasy.presentation.main.list

import android.content.Intent
import androidx.fragment.app.viewModels
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentListBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import com.surveasy.surveasy.presentation.main.survey.SurveyActivity
import com.surveasy.surveasy.presentation.main.survey.fs.FirstSurveyActivity
import com.surveasy.surveasy.presentation.util.IntentId.SURVEY_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>(R.layout.fragment_list) {
    private val viewModel: ListViewModel by viewModels()
    private val adapter = SurveyListAdapter {
        viewModel.navigateToSurveyDetail(it)
    }

    override fun initView() = with(binding) {
        vm = viewModel
        rvList.adapter = adapter
    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is ListEvents.ClickSurveyItem -> toSurveyDetail(it.id)
                    is ListEvents.ShowSnackBar -> showSnackBar(it.msg)
                    is ListEvents.NavigateToFs -> toFs()
                    else -> Unit
                }
            }
        }
    }

    override fun initData() {
        viewModel.listSurvey()
    }

    private fun toSurveyDetail(id: Int) {
        val intent = Intent(context, SurveyActivity::class.java)
        intent.putExtra(SURVEY_ID, id)
        startActivity(intent)
    }

    private fun toFs() {
        val intent = Intent(context, FirstSurveyActivity::class.java)
        startActivity(intent)
    }
}