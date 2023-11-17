package com.surveasy.surveasy.presentation.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentRegisterInput2Binding
import com.surveasy.surveasy.presentation.base.BaseFragment

class RegisterInput2Fragment : BaseFragment<FragmentRegisterInput2Binding>(FragmentRegisterInput2Binding::inflate) {
    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
    }

    private fun initView(view: View) {
        navController = Navigation.findNavController(view)
        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            navController = Navigation.findNavController(view)

            repeatOnStarted {
                viewModel.events.collect { event ->
                    when (event) {
                        is RegisterEvents.NavigateToBack ->
                            navController.navigate(RegisterInput2FragmentDirections.actionRegisterInput2FragmentToRegisterInput1Fragment())
                    }
                }
            }

        }
    }
}