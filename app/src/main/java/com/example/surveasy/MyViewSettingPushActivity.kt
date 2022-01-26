package com.example.surveasy

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MyViewSettingPushActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_myviewsettingpush)

        val settingPushToolbar : Toolbar? = findViewById(R.id.ToolbarMyViewSettingPush)
        setSupportActionBar(settingPushToolbar)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        settingPushToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }


    }
}