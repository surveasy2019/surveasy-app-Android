package com.example.surveasy.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.surveasy.MainActivity
import com.example.surveasy.R
import com.example.surveasy.databinding.ActivitySurveyDoneAlertDialogBinding

class SurveyDoneAlertDialogActivity : AppCompatActivity() {
    lateinit var binding : ActivitySurveyDoneAlertDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurveyDoneAlertDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SurveyAlertDialogBackBtn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}