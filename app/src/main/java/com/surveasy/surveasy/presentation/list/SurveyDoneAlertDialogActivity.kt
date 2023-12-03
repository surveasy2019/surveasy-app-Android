package com.surveasy.surveasy.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.surveasy.surveasy.presentation.Main1Activity
import com.surveasy.surveasy.databinding.ActivitySurveyDoneAlertDialogBinding

class SurveyDoneAlertDialogActivity : AppCompatActivity() {
    lateinit var binding : ActivitySurveyDoneAlertDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurveyDoneAlertDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SurveyAlertDialogBackBtn.setOnClickListener {
            val intent = Intent(this, Main1Activity::class.java)
            startActivity(intent)
        }
    }
}