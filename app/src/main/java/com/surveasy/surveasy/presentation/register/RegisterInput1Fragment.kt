package com.surveasy.surveasy.presentation.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentRegisterInput1Binding
import com.surveasy.surveasy.presentation.base.BaseFragment

class RegisterInput1Fragment : BaseFragment<FragmentRegisterInput1Binding>(FragmentRegisterInput1Binding::inflate) {
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        bind {
            RegisterFragment1Btn.setOnClickListener {
                navController.navigate(
                    RegisterInput1FragmentDirections.actionRegisterInput1FragmentToRegisterInput2Fragment()
                )
            }
        }
    }

}