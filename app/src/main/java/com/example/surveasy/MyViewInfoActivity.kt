package com.example.surveasy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MyViewInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_myviewinfo)

        val infoToolbar : Toolbar? = findViewById(R.id.ToolbarMyViewInfo)
        setSupportActionBar(infoToolbar)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        infoToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}