package com.surveasy.surveasy.presentation.intro.login

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentLoginBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import com.surveasy.surveasy.presentation.intro.RegisterEvents
import com.surveasy.surveasy.presentation.intro.RegisterViewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login){
    private val viewModel: RegisterViewModel by viewModels()
    override fun initView() {
        bind {
            vm = viewModel
        }
    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect { event ->
                when (event) {
                    is RegisterEvents.NavigateToExistLogin -> findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToExistUserFragment()
                    )

                    is RegisterEvents.NavigateToRegisterAgree -> findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToRegisterAgreeFragment()
                    )

                    is RegisterEvents.NavigateToBack -> findNavController().navigateUp()
                    else -> Unit
                }
            }
        }
    }

    override fun initData() {

    }

}