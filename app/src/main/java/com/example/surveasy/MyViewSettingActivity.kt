package com.example.surveasy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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