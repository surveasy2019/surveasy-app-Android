package com.surveasy.surveasy.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentRegisterAgreeBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import kotlinx.coroutines.flow.collect


class RegisterAgreeFragment :
    BaseFragment<FragmentRegisterAgreeBinding>(R.layout.fragment_register_agree) {
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
                        is RegisterEvents.NavigateToRegisterWarn ->
                            navController.navigate(RegisterAgreeFragmentDirections.actionRegisterAgreeFragmentToRegisterWarnFragment())
                        else -> Unit
                    }
                }
            }

        }
    }

}