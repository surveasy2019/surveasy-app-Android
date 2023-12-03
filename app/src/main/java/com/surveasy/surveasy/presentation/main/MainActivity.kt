package com.surveasy.surveasy.presentation.main

import android.os.Bundle
import android.util.Log
import android.view.View
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        Log.d("TEST", "start")
    }

    private fun initView(){
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        with(binding) {
            bnvNavBar.setupWithNavController(navController)

//            navController.addOnDestinationChangedListener { _, destination, _ ->
//                if (destination.id == R.id.homeFragment || destination.id == R.id.listFragment || destination.id == R.id.myFragment) {
//                    bnvNavBar.visibility = View.VISIBLE
//                } else {
//                    bnvNavBar.visibility = View.GONE
//                }
//            }
        }
    }
}