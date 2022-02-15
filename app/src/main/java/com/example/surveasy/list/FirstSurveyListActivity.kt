package com.example.surveasy.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.surveasy.R
import com.example.surveasy.databinding.ActivityFirstSurveyListBinding
import com.example.surveasy.list.firstsurvey.SurveyListFirstSurveyActivity
import com.example.surveasy.login.CurrentUser

//first survey 만 있는 리스트
class FirstSurveyListActivity : AppCompatActivity() {

    lateinit var binding : ActivityFirstSurveyListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstSurveyListBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.FirstSurveyListItemTitle.text = "First Survey"

        val currentUser = intent.getParcelableExtra<CurrentUser>("currentUser_main")

        binding.FirstSurveyListItemContainer.setOnClickListener{
            val intent = Intent(this,SurveyListFirstSurveyActivity::class.java)
            val userList = intent.putExtra("currentUser_main", currentUser)
            startActivityForResult(intent,101)
        }



    }
}