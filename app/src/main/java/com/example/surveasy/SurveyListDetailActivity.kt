package com.example.surveasy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.example.surveasy.databinding.ActivitySurveylistdetailBinding


class SurveyListDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySurveylistdetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySurveylistdetailBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_surveylistdetail)

        binding.SurveyListDetailBtn.setOnClickListener {
            val intent = Intent(this,SurveyListDetailResponseActivity::class.java)
            startActivity(intent)
        }
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