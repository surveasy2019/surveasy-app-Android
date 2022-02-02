package com.example.surveasy.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.surveasy.databinding.ActivitySurveylistdetailresponseproofBinding



class SurveyListDetailResponseProof : AppCompatActivity() {




    private lateinit var binding : ActivitySurveylistdetailresponseproofBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySurveylistdetailresponseproofBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.ToolbarSurveyListDetailResponseProof)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarSurveyListDetailResponseProof.setNavigationOnClickListener {
            onBackPressed()
        }


    }


}