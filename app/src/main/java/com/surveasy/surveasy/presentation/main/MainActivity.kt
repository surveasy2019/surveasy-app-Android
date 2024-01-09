package com.surveasy.surveasy.presentation.main

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivityMainBinding
import com.surveasy.surveasy.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun initData() = Unit

    override fun initEventObserver() = Unit

    override fun initView() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bnvNavBar.setupWithNavController(navController)
    }

}