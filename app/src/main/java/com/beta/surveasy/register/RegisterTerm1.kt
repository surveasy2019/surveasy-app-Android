package com.beta.surveasy.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.beta.surveasy.R

class RegisterTerm1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_term1)

        val term1Toolbar : Toolbar? = findViewById(R.id.ToolbarRegisterAgree1)
        setSupportActionBar(term1Toolbar)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        term1Toolbar?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}