package com.example.surveasy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)
        val goListBtn : Button = findViewById(R.id.HomeToList)
        val goMyBtn : Button = findViewById(R.id.HomeToMy)
        val goRegister : Button = findViewById(R.id.HomeToRegister)
        goListBtn.setOnClickListener {
            val intent = Intent(this,SurveyListActivity::class.java)
            startActivity(intent)
        }
        goMyBtn.setOnClickListener {
            val intent = Intent(this,MyViewNoticeListActivity::class.java)
            startActivity(intent)
        }
        goRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}