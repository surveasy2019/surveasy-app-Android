package com.surveasy.surveasy.presentation.main.my.edit

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivityEditBinding
import com.surveasy.surveasy.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EditActivity : BaseActivity<ActivityEditBinding>(ActivityEditBinding::inflate) {
    private val viewModel: MyEditViewModel by viewModels()
    private var initBank = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        initEventObserver()
        initBankSpinner()
        binding.vm = viewModel
    }

    private fun initData() {
        viewModel.queryPanelDetailInfo()
    }

    private fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is MyEditUiEvents.DoneEdit -> {
                        showToastMessage("수정이 완료되었습니다.")
                        initData()
                    }
                }
            }
        }

        repeatOnStarted {
            viewModel.editBank.collectLatest {
                initBank = it
            }
        }
    }

    private fun initBankSpinner() {
        val bankList = resources.getStringArray(R.array.accountType)
        val bankAdapter =
            ArrayAdapter(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                bankList
            )

        bind {
            sBank.apply {
                adapter = bankAdapter
                setSelection(
                    if (initBank.isEmpty()) 0
                    else bankList.indexOf(initBank)
                )
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        viewModel.setBank(bankList[position])
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }
                }
            }
        }
    }
}