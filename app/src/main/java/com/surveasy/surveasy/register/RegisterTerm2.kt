package com.surveasy.surveasy.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.surveasy.surveasy.R

class RegisterTerm2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_term2)

        val term2Toolbar : Toolbar? = findViewById(R.id.ToolbarRegisterAgree2)
        setSupportActionBar(term2Toolbar)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        term2Toolbar?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}