package com.example.surveasy.list

import android.content.ContentValues.TAG
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.example.surveasy.R
import com.example.surveasy.databinding.ActivitySurveylistdetailBinding


class SurveyListDetailActivity : AppCompatActivity() {



    private lateinit var binding: ActivitySurveylistdetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivitySurveylistdetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.SurveyListDetailBtn.setOnClickListener {
            val intent = Intent(this, SurveyListDetailResponseActivity::class.java)
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

        val detailIndex : Int = intent.getIntExtra("index",0)

        binding.SurveyListDetailInfo.text = detailIndex.toString()




    }
}