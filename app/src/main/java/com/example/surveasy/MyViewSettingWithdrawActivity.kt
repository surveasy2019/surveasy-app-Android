package com.example.surveasy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.surveasy.databinding.ActivityMyviewsettingwithdrawBinding

class MyViewSettingWithdrawActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyviewsettingwithdrawBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyviewsettingwithdrawBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.ToolbarMyViewSettingWithdraw)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarMyViewSettingWithdraw.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}