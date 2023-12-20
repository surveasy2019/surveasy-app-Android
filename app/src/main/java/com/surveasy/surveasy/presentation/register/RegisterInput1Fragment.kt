package com.surveasy.surveasy.presentation.register

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentRegisterInput1Binding
import com.surveasy.surveasy.presentation.base.BaseFragment
import com.surveasy.surveasy.presentation.util.showCalendarDatePicker

class RegisterInput1Fragment :
    BaseFragment<FragmentRegisterInput1Binding>(R.layout.fragment_register_input1) {
    private val viewModel: RegisterViewModel by viewModels()

    override fun initEventObserver() {

    }

    override fun initView() {
        initDatePicker()
        initInflowPathSpinner()
        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            repeatOnStarted {
                viewModel.events.collect { event ->
                    when (event) {
                        is RegisterEvents.NavigateToRegisterInput2 -> findNavController().navigate(
                            RegisterInput1FragmentDirections.actionRegisterInput1FragmentToRegisterInput2Fragment()
                        )

                        is RegisterEvents.NavigateToBack -> findNavController().navigateUp()
                        else -> Unit
                    }
                }
            }

        }
    }

    private fun initDatePicker() {
        bind {
            ivCalendar.setOnClickListener {
                showCalendarDatePicker(parentFragmentManager) {
                    viewModel.setBirth(it)
                }
            }
        }
    }

    private fun initInflowPathSpinner() {
        val inflowPathList = resources.getStringArray(R.array.inflowPath)
        val inflowPathAdapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            inflowPathList
        )

        bind {
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
    }
}

