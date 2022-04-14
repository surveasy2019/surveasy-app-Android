package com.beta.surveasy.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.beta.surveasy.databinding.ActivityNetworkAlertBinding


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
