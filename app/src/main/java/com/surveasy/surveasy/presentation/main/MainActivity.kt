package com.surveasy.surveasy.presentation.main

import android.content.Intent
import android.net.Uri
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivityMainBinding
import com.surveasy.surveasy.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var appUpdateManager: AppUpdateManager
    private val viewModel: MainViewModel by viewModels()

    override fun initData() = Unit

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is MainEvents.FinishApp -> finish()
                    else -> Unit
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
        lifecycleScope.launch { viewModel.checkVersion(appVersion) }

        appUpdateManager = AppUpdateManagerFactory.create(this)
    }

    private fun goStore() {
        val uri = "https://play.google.com/store/apps/details?id=com.surveasy.surveasy"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }

//    private fun checkUpdate() {
//        var appUpdateInfoTask = appUpdateManager.appUpdateInfo
//        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
//
//            // 업데이트 가능한 구버전 상태
//            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
//                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
//            ) {
//                // Request the update.
//                appUpdateManager.startUpdateFlowForResult(
//                    appUpdateInfo,
//                    AppUpdateType.IMMEDIATE,
//                    this,
//                    REQUEST_CODE_UPDATE
//                )
//
//                appUpdateManager.startUpdateFlowForResult(
//                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
//                    appUpdateInfo,
//                    // an activity result launcher registered via registerForActivityResult
//                    activityResultLauncher,
//                    // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
//                    // flexible updates.
//                    AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build())
//
//            }
//
//            else {
//                // 업데이트 필요 없는 최신 상태
//            }
//
//
//        }
//    }

//    // 업데이트 취소나 실패 콜백
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == REQUEST_CODE_UPDATE) {
//            if (resultCode != RESULT_OK) {
//                Log.e("MY_APP", "Update flow failed! Result code: $resultCode")
//
//            }
//        }
//    }
//
//
//    // 어플이 다시 불러와졌을 경우, 업데이트 이어서 계속 진행
//    override fun onResume() {
//        super.onResume()
//
//        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
//            if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
//                // If an in-app update is already running, resume the update.
//                appUpdateManager.startUpdateFlowForResult(
//                    appUpdateInfo,
//                    AppUpdateType.IMMEDIATE,
//                    this,
//                    REQUEST_CODE_UPDATE)
//
//            }
//        }
//
//    }
//
//    companion object {
//        const val REQUEST_CODE_UPDATE = 9999
//    }

}