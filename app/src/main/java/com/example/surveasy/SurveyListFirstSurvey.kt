package com.example.surveasy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class SurveyListFirstSurvey : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_surveylistfirstsurvey)

        val firstSurveyToolbar : Toolbar? = findViewById(R.id.ToolbarFirstSurvey)
        setSupportActionBar(firstSurveyToolbar)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        firstSurveyToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}