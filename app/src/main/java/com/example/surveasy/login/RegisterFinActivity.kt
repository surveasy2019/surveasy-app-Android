package com.example.surveasy.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.surveasy.MainActivity
import com.example.surveasy.R
import com.example.surveasy.databinding.ActivityRegisterfinBinding
import com.example.surveasy.list.SurveyListFirstSurveyActivity

class RegisterFinActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterfinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registerfin)

        binding = ActivityRegisterfinBinding.inflate(layoutInflater)

        binding.RegisterFinFirstBtn.setOnClickListener {
            val intent : Intent = Intent(this, SurveyListFirstSurveyActivity::class.java )
            startActivity(intent)
        }

        binding.RegisterFinHomeBtn.setOnClickListener {
            val intent : Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}