package com.example.surveasy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.surveasy.databinding.ActivitySurveylistdetailBinding

class SurveyListDetailResponseActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySurveylistdetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySurveylistdetailBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_surveylistdetailresponse)

        setSupportActionBar(binding.ToolbarSurveyListDetail)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarSurveyListDetail.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}