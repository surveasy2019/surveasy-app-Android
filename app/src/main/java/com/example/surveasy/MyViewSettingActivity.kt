package com.example.surveasy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MyViewSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_myviewsetting)

        val myViewSettingPush : Button = findViewById(R.id.MyViewSetting_Push)
        myViewSettingPush.setOnClickListener {
            intent = Intent(this, MyViewActivity::class.java)
            startActivity(intent)
        }

        val myViewSettingWithdraw : Button = findViewById(R.id.MyViewSetting_Withdraw)
        myViewSettingWithdraw.setOnClickListener {
            intent = Intent(this, MyViewSettingPushActivity::class.java)
            startActivity(intent)
        }




    }
}