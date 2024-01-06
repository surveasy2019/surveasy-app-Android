package com.surveasy.surveasy.presentation.main.survey

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivitySurveyBinding
import com.surveasy.surveasy.presentation.base.BaseActivity
import com.surveasy.surveasy.presentation.util.IntentId.SURVEY_ID

class SurveyActivity : BaseActivity<ActivitySurveyBinding>(ActivitySurveyBinding::inflate) {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        initData()
    }

    private fun initData(){
        val sId = intent.getIntExtra(SURVEY_ID, -1)
        showToastMessage(sId.toString())
    }
}