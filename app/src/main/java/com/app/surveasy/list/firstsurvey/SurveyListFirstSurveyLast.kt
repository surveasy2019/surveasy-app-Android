package com.app.surveasy.list.firstsurvey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.surveasy.MainActivity
import com.app.surveasy.databinding.ActivitySurveyListFirstSurveyLastBinding

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