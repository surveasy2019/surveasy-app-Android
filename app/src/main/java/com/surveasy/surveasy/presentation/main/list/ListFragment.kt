package com.surveasy.surveasy.presentation.main.list

import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentListBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import com.surveasy.surveasy.presentation.main.MainViewModel
import com.surveasy.surveasy.presentation.main.survey.SurveyActivity
import com.surveasy.surveasy.presentation.main.survey.fs.FirstSurveyActivity
import com.surveasy.surveasy.presentation.util.IntentId.SURVEY_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>(R.layout.fragment_list) {
    private val viewModel: ListViewModel by viewModels()
    private val parentViewModel: MainViewModel by activityViewModels()
    private val adapter = SurveyListAdapter {
        viewModel.navigateToSurveyDetail(it)
    }

    override fun initView() = with(binding) {
        vm = viewModel
        finishApp()
        rvList.adapter = adapter
        rvList.animation = null
        rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val scrollBottom = !rvList.canScrollVertically(1)
                val isLastPage = viewModel.uiState.value.lastPage
                val isNotLoading = !viewModel.uiState.value.isLoading


                if (scrollBottom && !isLastPage && isNotLoading) {
                    viewModel.loadNextPage()
                }
            }
        })
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

    private fun finishApp() {
        var backPressTime = 0L
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - backPressTime <= 2000) {
                    parentViewModel.finishApp()
                } else {
                    backPressTime = System.currentTimeMillis()
                    showToastMessage("뒤로가기 버튼을 한 번 더 누르면 종료됩니다.")
                }
            }
        })
    }
}