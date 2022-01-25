package com.example.surveasy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SurveyListDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_surveylistdetail)

        val surveyListBtn : Button = findViewById(R.id.SurveyListDetail_Btn)
        surveyListBtn.setOnClickListener {
            val intent = Intent(this,SurveyListDetailResponseActivity::class.java)
            startActivity(intent)
        }
    }
}