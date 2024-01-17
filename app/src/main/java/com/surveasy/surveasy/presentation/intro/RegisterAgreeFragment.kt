package com.surveasy.surveasy.presentation.intro

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

}