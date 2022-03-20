package com.example.surveasy.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.surveasy.R
import com.example.surveasy.databinding.ActivityNetworkAlertBinding


import com.example.surveasy.login.CurrentUser


class NetworkAlertActivity : AppCompatActivity() {

    lateinit var binding : ActivityNetworkAlertBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNetworkAlertBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.networkAlertOkBtn.setOnClickListener {
            finishAffinity()
        }


    }
}
