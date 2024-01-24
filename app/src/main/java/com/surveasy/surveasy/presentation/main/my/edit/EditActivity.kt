package com.surveasy.surveasy.presentation.main.my.edit

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

    override fun initData() {
        viewModel.queryPanelDetailInfo()
    }

    override fun initView() {
        initData()
        initEventObserver()
        initBankSpinner()
        binding.vm = viewModel
    }


    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is MyEditUiEvents.DoneEdit -> initData()
                    is MyEditUiEvents.NavigateToBack -> finish()
                    is MyEditUiEvents.ShowSnackBar -> showSnackBar(it.msg, it.btn)
                    is MyEditUiEvents.ShowToastMsg -> showToastMessage(it.msg)
                }
            }
        }
    }

    private fun initBankSpinner() = with(binding) {
        val bankList = resources.getStringArray(R.array.accountType)
        val bankAdapter =
            ArrayAdapter(
                this@EditActivity,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                bankList
            )

        sBank.apply {
            adapter = bankAdapter
            repeatOnStarted {
                viewModel.editBank.collectLatest {
                    setSelection(
                        if (it.isEmpty()) 0
                        else bankList.indexOf(it)
                    )
                }
            }

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