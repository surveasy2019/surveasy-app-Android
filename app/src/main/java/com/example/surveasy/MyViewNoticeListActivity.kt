package com.example.surveasy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.example.surveasy.databinding.ActivityMyviewnoticelistBinding

class MyViewNoticeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyviewnoticelistBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyviewnoticelistBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.ToolbarMyViewNoticeList)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.MyViewNoticeListItemTitle.setOnClickListener {
            val intent = Intent(this,MyViewNoticeListDetailActivity::class.java)
            startActivity(intent)
        }
        binding.MyViewNoticeListItemPinTitle.setOnClickListener {
            val intent = Intent(this,MyViewNoticeListDetailActivity::class.java)
            startActivity(intent)
        }

        binding.ToolbarMyViewNoticeList.setNavigationOnClickListener {
            onBackPressed()
        }



    }
}