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
import com.surveasy.surveasy.databinding.FragmentRegisterInput1Binding
import com.surveasy.surveasy.presentation.base.BaseFragment
import com.surveasy.surveasy.presentation.util.showCalendarDatePicker
import java.util.Calendar

class RegisterInput1Fragment :
    BaseFragment<FragmentRegisterInput1Binding>(FragmentRegisterInput1Binding::inflate) {
    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        initDatePicker()

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
                        is RegisterEvents.NavigateToRegisterInput2 -> navController.navigate(
                            RegisterInput1FragmentDirections.actionRegisterInput1FragmentToRegisterInput2Fragment()
                        )

                        is RegisterEvents.NavigateToBack -> navController.navigate(
                            RegisterInput1FragmentDirections.actionRegisterInput1FragmentToRegisterWarnFragment()
                        )
                    }
                }
            }

        }
    }

    private fun initDatePicker() {
        bind {
            ivCalendar.setOnClickListener { showCalendarDatePicker(parentFragmentManager){
                viewModel.setBirth(it)
            } }
        }
    }
}

