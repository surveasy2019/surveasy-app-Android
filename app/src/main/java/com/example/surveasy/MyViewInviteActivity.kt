package com.example.surveasy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MyViewInviteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_myviewinvite)

        val inviteToolbar : Toolbar? = findViewById(R.id.ToolbarMyViewInvite)
        setSupportActionBar(inviteToolbar)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        inviteToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}