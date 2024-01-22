package com.surveasy.surveasy.presentation.main.home

import android.content.Intent
import android.util.Log
import androidx.fragment.app.viewModels
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentHomeBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import com.surveasy.surveasy.presentation.main.home.list.HomeListAdapter
import com.surveasy.surveasy.presentation.main.home.notice.HomeHowContentActivity
import com.surveasy.surveasy.presentation.main.home.notice.HomeNoticeActivity
import com.surveasy.surveasy.presentation.main.survey.SurveyActivity
import com.surveasy.surveasy.presentation.main.survey.fs.FirstSurveyActivity
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
        rvHomeList.animation = null
    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is HomeEvents.ClickSurveyItem -> toSurveyDetail(it.id)
                    is HomeEvents.ShowSnackBar -> showSnackBar(it.msg)
                    is HomeEvents.ClickHowContent -> toHowContent()
                    is HomeEvents.ClickNotice -> toNotice()
                    is HomeEvents.NavigateToFs -> toFs()
                    else -> Unit
                }
            }
        }
    }

    private fun toSurveyDetail(id: Int) {
        val intent = Intent(context, SurveyActivity::class.java)
        intent.putExtra(IntentId.SURVEY_ID, id)
        startActivity(intent)
    }

    private fun toHowContent() {
        startActivity(Intent(context, HomeHowContentActivity::class.java))
    }

    private fun toNotice() {
        startActivity(Intent(context, HomeNoticeActivity::class.java))
    }

    private fun toFs() {
        val intent = Intent(context, FirstSurveyActivity::class.java)
        startActivity(intent)
    }
}
