package com.surveasy.surveasy.presentation.intro

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentRegisterInputBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
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
                    is RegisterEvents.NavigateToDone -> findNavController().toDone()
                    is RegisterEvents.ShowLoading -> showLoading(requireContext())
                    is RegisterEvents.DismissLoading -> dismissLoading()
                    is RegisterEvents.ShowSnackBar -> showSnackBar(event.msg)
                    else -> Unit
                }
            }
        }
    }

    override fun initView() = with(binding) {
        initSpinner()
        vm = viewModel
    }

    private fun initSpinner() = with(binding) {
        sBank.setAdapter(resources.getStringArray(R.array.accountType)) { viewModel.setBank(it) }
        sInflow.setAdapter(resources.getStringArray(R.array.inflowPath)) { viewModel.setInflow(it) }
    }

    private fun initSpinnerAdapter(list: Array<String>): ArrayAdapter<String> = ArrayAdapter(
        requireContext(),
        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
        list
    )

    private fun Spinner.setAdapter(
        list: Array<String>,
        itemSelectListener: (item: String) -> Unit
    ) = apply {
        adapter = initSpinnerAdapter(list)
        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                itemSelectListener(list[position])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) = Unit
        }
    }

    private fun NavController.toDone() {
        navigate(RegisterInputFragmentDirections.actionRegisterInputFragmentToRegisterDoneFragment())
    }
}