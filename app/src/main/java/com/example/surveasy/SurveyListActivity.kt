package com.example.surveasy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout


class SurveyListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_surveylist)

        val surveyListItemContainer : ConstraintLayout = findViewById(R.id.SurveyList_ItemContainer)
        surveyListItemContainer.setOnClickListener {
            val intent = Intent(this,SurveyListDetailActivity::class.java)
            startActivity(intent)
        }
    }
}