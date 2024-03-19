package com.surveasy.surveasy.presentation.main

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivityMainBinding
import com.surveasy.surveasy.presentation.base.BaseActivity
import com.surveasy.surveasy.presentation.util.showTwoButtonDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    override fun initData() = Unit

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is MainEvents.FinishApp -> finish()
                    is MainEvents.UpdateDialog -> {
                        showTwoButtonDialog(
                            this@MainActivity,
                            "업데이트 안내",
                            resources.getString(R.string.update_warn),
                            "업데이트 하러 가기",
                            "",
                            { goStore() }
                        ) {}
                    }
                }
            }
        }
    }

    override fun initView() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bnvNavBar.setupWithNavController(navController)
        val appVersion = packageManager.getPackageInfo(packageName, 0).versionName
        Log.d("TAG", "initView: $appVersion")
        lifecycleScope.launch { viewModel.checkVersion(appVersion) }

    }

    private fun goStore() {
        val uri = "https://play.google.com/store/apps/details?id=com.surveasy.surveasy"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }

}