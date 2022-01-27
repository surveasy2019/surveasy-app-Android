package com.example.surveasy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.surveasy.databinding.ActivityMyviewnoticelistdetailBinding

class MyViewNoticeListDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyviewnoticelistdetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyviewnoticelistdetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.ToolbarMyViewNoticeListDetail)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarMyViewNoticeListDetail.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}