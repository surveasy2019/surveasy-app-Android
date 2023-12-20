package com.surveasy.surveasy.presentation.main.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.Navigation
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentHomeBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(view)
        Log.d("TEST", "home")
    }
}