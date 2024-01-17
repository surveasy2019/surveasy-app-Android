package com.surveasy.surveasy.presentation.intro

import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentRegisterInputBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import com.surveasy.surveasy.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterInputFragment :
    BaseFragment<FragmentRegisterInputBinding>(R.layout.fragment_register_input) {
    private val viewModel: RegisterViewModel by activityViewModels()

    override fun initData() = Unit

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect { event ->
                when (event) {
                    is RegisterEvents.NavigateToBack -> findNavController().navigateUp()
                    is RegisterEvents.NavigateToMain -> navigateToMain()
                    is RegisterEvents.ShowLoading -> showLoading(requireContext())
                    is RegisterEvents.DismissLoading -> dismissLoading()
                    is RegisterEvents.ShowSnackBar -> showSnackBar(event.msg)
                    else -> Unit
                }
            }
        }
    }

    override fun initView() = with(binding) {
        initBankSpinner()
        initInflowPathSpinner()
        vm = viewModel
    }

    private fun initBankSpinner() = with(binding) {
        val bankList = resources.getStringArray(R.array.accountType)
        val bankAdapter =
            ArrayAdapter(
                requireContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                bankList
            )
        sBank.apply {
            adapter = bankAdapter
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

    private fun initInflowPathSpinner() = with(binding) {
        val inflowPathList = resources.getStringArray(R.array.inflowPath)
        val inflowPathAdapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            inflowPathList
        )

        sInflow.apply {
            adapter = inflowPathAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.setInflow(inflowPathList[position])
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        }
    }

    private fun navigateToMain() {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}