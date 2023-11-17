package com.surveasy.surveasy.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.surveasy.surveasy.databinding.FragmentRegisterAgreeBinding
import com.surveasy.surveasy.presentation.base.BaseFragment


class RegisterAgreeFragment :
    BaseFragment<FragmentRegisterAgreeBinding>(FragmentRegisterAgreeBinding::inflate) {
    private val viewModel : RegisterViewModel by activityViewModels()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            btnNext.setOnClickListener {
                navController.navigate(
                    RegisterAgreeFragmentDirections.actionRegisterAgreeFragmentToRegisterWarnFragment()
                )
            }

        }
    }

}