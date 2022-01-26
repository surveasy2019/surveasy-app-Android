package com.example.surveasy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar

class MyViewNoticeListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_myviewnoticelist)

        val noticeListToolbar : Toolbar? = findViewById(R.id.ToolbarMyViewNoticeList)
        setSupportActionBar(noticeListToolbar)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        noticeListToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }


    }
}