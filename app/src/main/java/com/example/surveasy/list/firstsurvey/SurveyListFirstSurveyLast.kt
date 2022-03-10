package com.example.surveasy.list.firstsurvey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.surveasy.MainActivity
import com.example.surveasy.R
import com.example.surveasy.databinding.ActivityFirstSurveyListBinding
import com.example.surveasy.databinding.ActivitySurveyListFirstSurveyLastBinding

class SurveyListFirstSurveyLast : AppCompatActivity() {
    lateinit var binding : ActivitySurveyListFirstSurveyLastBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivitySurveyListFirstSurveyLastBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.ToolbarFirstSurveyLast)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarFirstSurveyLast.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.SurveyListFirstSurveyLastBtn.setOnClickListener {
            val intent : Intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}