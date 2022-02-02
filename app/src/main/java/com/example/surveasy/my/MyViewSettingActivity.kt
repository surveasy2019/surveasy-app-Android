package com.example.surveasy.my

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.surveasy.databinding.ActivityMyviewsettingBinding

class MyViewSettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyviewsettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyviewsettingBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.MyViewSettingPush.setOnClickListener {
            val intent = Intent(this, MyViewSettingPushActivity::class.java)
            startActivity(intent)
        }

        binding.MyViewSettingWithdraw.setOnClickListener {
            val intent = Intent(this, MyViewSettingWithdrawActivity::class.java)
            startActivity(intent)
        }

        setSupportActionBar(binding.ToolbarMyViewSetting)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarMyViewSetting.setNavigationOnClickListener {
            onBackPressed()
        }




    }
}