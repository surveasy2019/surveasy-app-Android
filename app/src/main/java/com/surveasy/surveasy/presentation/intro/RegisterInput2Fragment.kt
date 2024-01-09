package com.surveasy.surveasy.presentation.intro

import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentRegisterInput2Binding
import com.surveasy.surveasy.presentation.base.BaseFragment
import com.surveasy.surveasy.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterInput2Fragment :
    BaseFragment<FragmentRegisterInput2Binding>(R.layout.fragment_register_input2) {
    private val viewModel: RegisterViewModel by viewModels()

    override fun initEventObserver() {

    }

    override fun initData() {

    }

    override fun initView() {
        initBankSpinner()
        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            repeatOnStarted {
                viewModel.events.collect { event ->
                    when (event) {
                        is RegisterEvents.NavigateToBack ->
                            findNavController().navigateUp()

                        is RegisterEvents.NavigateToMain -> navigateToMain()
                        else -> Unit
                    }
                }
            }

        }
    }

    private fun initBankSpinner() {
        val bankList = resources.getStringArray(R.array.accountType)
        val bankAdapter =
            ArrayAdapter(
                requireContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                bankList
            )

        bind {
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
    }

    private fun navigateToMain() {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}