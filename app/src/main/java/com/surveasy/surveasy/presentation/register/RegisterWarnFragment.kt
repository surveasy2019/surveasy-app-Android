package com.surveasy.surveasy.presentation.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentRegisterWarnBinding
import com.surveasy.surveasy.presentation.base.BaseFragment

class RegisterWarnFragment :
    BaseFragment<FragmentRegisterWarnBinding>(FragmentRegisterWarnBinding::inflate) {
    private val vm : RegisterViewModel by activityViewModels()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        bind {
            btnCheck.setOnClickListener {
                navController.navigate(
                    RegisterWarnFragmentDirections.actionRegisterWarnFragmentToRegisterInput1Fragment()
                )
            }
        }
    }
}