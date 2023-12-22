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
import androidx.navigation.fragment.findNavController
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentRegisterAgreeBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import kotlinx.coroutines.flow.collect


class RegisterAgreeFragment :
    BaseFragment<FragmentRegisterAgreeBinding>(R.layout.fragment_register_agree) {
    private val viewModel: RegisterViewModel by viewModels()



    override fun initView() {
        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect { event ->
                when (event) {
                    is RegisterEvents.NavigateToRegisterWarn ->
                        findNavController().navigate(RegisterAgreeFragmentDirections.actionRegisterAgreeFragmentToRegisterWarnFragment())
                    else -> Unit
                }
            }
        }
    }

    override fun initData() {

    }

}