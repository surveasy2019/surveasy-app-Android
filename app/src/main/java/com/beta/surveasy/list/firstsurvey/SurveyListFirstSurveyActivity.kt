package com.beta.surveasy.list.firstsurvey

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.beta.surveasy.R
import com.beta.surveasy.databinding.ActivitySurveylistfirstsurveyBinding
import com.beta.surveasy.login.CurrentUser
import com.beta.surveasy.login.CurrentUserViewModel

class SurveyListFirstSurveyActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySurveylistfirstsurveyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userModel by viewModels<CurrentUserViewModel>()
        val firstSurveyViewModel by viewModels<FirstSurveyViewModel>()

        // Current User from MainActivity [P]
        val currentUser = intent.getParcelableExtra<CurrentUser>("currentUser_main")
        userModel.currentUser = currentUser!!
        Log.d(TAG, "~~~~~~~~~${currentUser.name}")


        binding = ActivitySurveylistfirstsurveyBinding.inflate(layoutInflater)

        setContentView(binding.root)


        setSupportActionBar(binding.ToolbarFirstSurvey)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarFirstSurvey.setNavigationOnClickListener {
            onBackPressed()
        }


        val transaction = supportFragmentManager.beginTransaction()
        setContentView(binding.root)
        transaction.add(R.id.SurveyListFirstSurvey_view, SurveyListFirstSurvey1Fragment()).commit()
    }

    fun next() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.SurveyListFirstSurvey_view, SurveyListFirstSurvey2Fragment())
            .commit()
    }

    fun fin(){
        finishAffinity()
    }

}