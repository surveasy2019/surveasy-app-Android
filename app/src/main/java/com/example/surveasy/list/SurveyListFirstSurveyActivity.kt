package com.example.surveasy.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.surveasy.databinding.ActivitySurveylistfirstsurveyBinding

class SurveyListFirstSurveyActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySurveylistfirstsurveyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    }
}