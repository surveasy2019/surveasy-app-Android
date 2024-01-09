package com.surveasy.surveasy.presentation.main.my

import android.content.Intent
import androidx.fragment.app.viewModels
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentMyBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import com.surveasy.surveasy.presentation.main.my.contact.ContactActivity
import com.surveasy.surveasy.presentation.main.my.edit.EditActivity
import com.surveasy.surveasy.presentation.main.my.history.HistoryActivity
import com.surveasy.surveasy.presentation.main.my.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFragment : BaseFragment<FragmentMyBinding>(R.layout.fragment_my) {
    private val viewModel: MyViewModel by viewModels()
    override fun initView() {
        bind {
            vm = viewModel
        }

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
                }
            }
        }
    }

    override fun initData() {
        viewModel.queryPanelInfo()
    }
}