package com.surveasy.surveasy.presentation.main.my

import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentMyBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import com.surveasy.surveasy.presentation.main.MainViewModel
import com.surveasy.surveasy.presentation.main.my.contact.ContactActivity
import com.surveasy.surveasy.presentation.main.my.edit.EditActivity
import com.surveasy.surveasy.presentation.main.my.history.HistoryActivity
import com.surveasy.surveasy.presentation.main.my.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFragment : BaseFragment<FragmentMyBinding>(R.layout.fragment_my) {
    private val viewModel: MyViewModel by viewModels()
    private val parentViewModel: MainViewModel by activityViewModels()
    override fun initView() = with(binding) {
        vm = viewModel
        finishApp()
    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect { event ->
                when (event) {
                    is MyUiEvents.NavigateToHistory -> startActivity(
                        Intent(
                            context,
                            HistoryActivity::class.java
                        )
                    )

                    is MyUiEvents.NavigateToEdit -> startActivity(
                        Intent(
                            context,
                            EditActivity::class.java
                        )
                    )

                    is MyUiEvents.NavigateToSetting -> startActivity(
                        Intent(
                            context,
                            SettingActivity::class.java
                        )
                    )

                    is MyUiEvents.NavigateToContact -> startActivity(
                        Intent(
                            context,
                            ContactActivity::class.java
                        )
                    )

                    is MyUiEvents.ShowSnackBar -> showSnackBar(event.msg)
                    else -> Unit
                }
            }
        }
    }

    override fun initData() {
        viewModel.queryPanelInfo()
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