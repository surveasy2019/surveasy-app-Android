package com.app.surveasy.my.notice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.surveasy.databinding.ActivityMyviewnoticelistdetailBinding

class MyViewNoticeListDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyviewnoticelistdetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyviewnoticelistdetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // ToolBar
        setSupportActionBar(binding.ToolbarMyViewNoticeListDetail)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        binding.ToolbarMyViewNoticeListDetail.setNavigationOnClickListener {
            onBackPressed()
        }

        val title = intent.getStringExtra("title")
        val date = intent.getStringExtra("date")
        val content = intent.getStringExtra("content")
        val fixed = intent.getBooleanExtra("fixed", false)

        if(fixed == true) binding.MyViewNoticeListDetailFixedImg.visibility = View.VISIBLE

        binding.MyViewNoticeListDetailTitle.text = title
        binding.MyViewNoticeListDetailDate.text = date
        binding.MyViewNoticeListDetailContent.text = content!!.replace("$", "\n\n")


    }
}