package com.surveasy.surveasy.presentation.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentRegisterWarnBinding
import com.surveasy.surveasy.presentation.base.BaseFragment

class RegisterWarnFragment :
    BaseFragment<FragmentRegisterWarnBinding>(R.layout.fragment_register_warn) {
    private val viewModel : RegisterViewModel by viewModels()

    override fun initView() {
        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            repeatOnStarted {
                viewModel.events.collect { event ->
                    when (event) {
                        is RegisterEvents.NavigateToRegisterInput1 -> findNavController().navigate(
                            RegisterWarnFragmentDirections.actionRegisterWarnFragmentToRegisterInput1Fragment()
                        )
                        is RegisterEvents.NavigateToBack -> findNavController().navigate(
                            RegisterWarnFragmentDirections.actionRegisterWarnFragmentToRegisterAgreeFragment()
                        )
                        else -> Unit
                    }
                }
            }

        }
    }

    override fun initEventObserver() {

    }

    override fun initData() {

    }
}