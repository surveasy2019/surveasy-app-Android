package com.app.surveasy.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.surveasy.MainActivity
import com.app.surveasy.databinding.ActivitySurveyDoneAlertDialogBinding

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