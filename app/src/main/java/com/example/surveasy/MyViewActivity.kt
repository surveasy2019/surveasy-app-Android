package com.example.surveasy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class MyViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_myview)

        val settingBtn : ImageButton = findViewById(R.id.MyView_SettingBtn)
        settingBtn.setOnClickListener {
            var intent = Intent(this, MyViewSettingActivity::class.java)
            startActivity(intent)
        }

        val historyIcon : ImageButton = findViewById(R.id.MyView_HistoryIcon)
        val historyText : TextView = findViewById(R.id.MyView_HistoryText)
        historyIcon.setOnClickListener {
            var intent = Intent(this, MyViewHistoryActivity::class.java)
            startActivity(intent)
        }

        val infoIcon : ImageButton = findViewById(R.id.MyView_InfoIcon)
        val infoText : TextView = findViewById(R.id.MyView_InfoText)
        historyIcon.setOnClickListener {
            var intent = Intent(this, MyViewInfoActivity::class.java)
            startActivity(intent)
        }

        val contactIcon : ImageButton = findViewById(R.id.MyView_ContactIcon)
        val contactText : TextView = findViewById(R.id.MyView_ContactText)
        contactIcon.setOnClickListener {
            var intent = Intent(this, MyViewContactActivity::class.java)
            startActivity(intent)
        }

        val inviteIcon : ImageButton = findViewById(R.id.MyView_InviteIcon)
        val inviteText : TextView = findViewById(R.id.MyView_InviteText)
        inviteIcon.setOnClickListener {
            var intent = Intent(this, MyViewInviteActivity::class.java)
            startActivity(intent)
        }


        val noticeMore : Button = findViewById(R.id.MyView_NoticeMore)
        noticeMore.setOnClickListener{
            var intent = Intent(this, MyViewNoticeListActivity::class.java)
            startActivity(intent)
        }



    }
}