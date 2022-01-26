package com.example.surveasy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MyViewContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_myviewcontact)

        val contactToolbar : Toolbar? = findViewById(R.id.ToolbarMyViewContact)
        setSupportActionBar(contactToolbar)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        contactToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}