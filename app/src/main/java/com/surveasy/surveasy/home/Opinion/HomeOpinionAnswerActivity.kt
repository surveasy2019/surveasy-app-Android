package com.surveasy.surveasy.home.Opinion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivityHomeOpinionAnswerBinding

class HomeOpinionAnswerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeOpinionAnswerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeOpinionAnswerBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //ToolBar
        setSupportActionBar(binding.ToolbarHomeOpinionAnswerDetail)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        binding.ToolbarHomeOpinionAnswerDetail.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}