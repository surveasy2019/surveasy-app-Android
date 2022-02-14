package com.example.surveasy.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.surveasy.R
import com.example.surveasy.databinding.ActivityRegisterfinBinding

class RegisterFinActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterfinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registerfin)

        binding = ActivityRegisterfinBinding.inflate(layoutInflater)

        binding.RegisterFinBtn.setOnClickListener {
            val intent : Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}