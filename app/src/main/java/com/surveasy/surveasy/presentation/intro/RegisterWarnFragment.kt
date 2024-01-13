package com.surveasy.surveasy.presentation.intro

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentRegisterWarnBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterWarnFragment :
    BaseFragment<FragmentRegisterWarnBinding>(R.layout.fragment_register_warn) {
    private val viewModel: RegisterViewModel by viewModels()

    override fun initView() = with(binding) {
        vm = viewModel
        lifecycleOwner = viewLifecycleOwner
    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect { event ->
                when (event) {
                    is RegisterEvents.NavigateToRegisterInput -> findNavController().navigate(
                        RegisterWarnFragmentDirections.actionRegisterWarnFragmentToRegisterInputFragment()
                    )

                    is RegisterEvents.NavigateToBack -> findNavController().navigateUp()
                    else -> Unit
                }
            }
        }
    }

    override fun initData() = Unit
}