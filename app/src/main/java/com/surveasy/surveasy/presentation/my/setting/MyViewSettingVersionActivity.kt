package com.surveasy.surveasy.my.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.surveasy.surveasy.databinding.ActivityMyviewsettingversionBinding

class MyViewSettingVersionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMyviewsettingversionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyviewsettingversionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tool bar
        setSupportActionBar(binding.ToolbarMyViewSettingVersion)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        binding.ToolbarMyViewSettingVersion.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}