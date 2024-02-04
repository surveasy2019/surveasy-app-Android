package com.surveasy.surveasy.presentation.intro.register

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentRegisterAgreeBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterAgreeFragment :
    BaseFragment<FragmentRegisterAgreeBinding>(R.layout.fragment_register_agree) {
    private val viewModel: RegisterViewModel by activityViewModels()


    override fun initView() = with(binding) {
        vm = viewModel
        initAgree()
    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect { event ->
                when (event) {
                    is RegisterEvents.NavigateToRegisterWarn ->
                        findNavController().navigate(RegisterAgreeFragmentDirections.actionRegisterAgreeFragmentToRegisterWarnFragment())

                    is RegisterEvents.NavigateToTerm1 -> findNavController().navigate(
                        RegisterAgreeFragmentDirections.actionRegisterAgreeFragmentToRegisterTerm1Fragment()
                    )

                    is RegisterEvents.NavigateToTerm2 -> findNavController().navigate(
                        RegisterAgreeFragmentDirections.actionRegisterAgreeFragmentToRegisterTerm2Fragment()
                    )

                    else -> Unit
                }
            }
        }
    }

    override fun initData() {

    }

    private fun initAgree() = with(binding) {
        cbAgree1.setOnClickListener {
            cbAgree2.isChecked = cbAgree1.isChecked
            cbAgree3.isChecked = cbAgree1.isChecked
            cbAgree4.isChecked = cbAgree1.isChecked
            viewModel.checkMust(cbAgree2.isChecked && cbAgree3.isChecked)

        }

        cbAgree2.setOnClickListener {
            cbAgree1.isChecked = checkAll()
            viewModel.checkMust(cbAgree2.isChecked && cbAgree3.isChecked)
        }

        cbAgree3.setOnClickListener {
            cbAgree1.isChecked = checkAll()
            viewModel.checkMust(cbAgree2.isChecked && cbAgree3.isChecked)
        }

        cbAgree4.setOnClickListener {
            cbAgree1.isChecked = checkAll()
            viewModel.checkMarketing(cbAgree4.isChecked)
        }
    }

    private fun checkAll(): Boolean =
        binding.cbAgree2.isChecked && binding.cbAgree3.isChecked && binding.cbAgree4.isChecked


}