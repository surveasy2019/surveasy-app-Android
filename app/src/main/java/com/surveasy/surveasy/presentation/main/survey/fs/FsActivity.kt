package com.surveasy.surveasy.presentation.main.survey.fs

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivityFsBinding
import com.surveasy.surveasy.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FsActivity : BaseActivity<ActivityFsBinding>(ActivityFsBinding::inflate) {
    private var navController: NavController? = null

    override fun initData() = Unit

    override fun initEventObserver() = Unit

    override fun initView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
    }
}