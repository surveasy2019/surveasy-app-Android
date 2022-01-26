package com.example.surveasy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MyViewSettingWithdrawActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_myviewsettingwithdraw)

        val withdrawToolbar : Toolbar? = findViewById(R.id.ToolbarMyViewSettingWithdraw)
        setSupportActionBar(withdrawToolbar)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        withdrawToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}