package com.example.surveasy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MyViewSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_myviewsetting)

        val myViewSettingPush : Button = findViewById(R.id.MyViewSetting_Push)
        myViewSettingPush.setOnClickListener {
            val intent = Intent(this, MyViewSettingPushActivity::class.java)
            startActivity(intent)
        }

        val myViewSettingWithdraw : Button = findViewById(R.id.MyViewSetting_Withdraw)
        myViewSettingWithdraw.setOnClickListener {
            val intent = Intent(this, MyViewSettingWithdrawActivity::class.java)
            startActivity(intent)
        }

        val settingToolbar : Toolbar? = findViewById(R.id.ToolbarMyViewSetting)
        setSupportActionBar(settingToolbar)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        settingToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }




    }
}