package com.test.surveasy.register

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.test.surveasy.R
import com.test.surveasy.login.CurrentUser

class AddInfoRegisterAgree2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_register_agree2)

        val currentUser = intent.getParcelableExtra<CurrentUser>("currentUser_login")!!
        val registerAgree2 : Button = findViewById(R.id.RegisterAgree2_Btn)
        val textColor : TextView = findViewById(R.id.changeColor_text)

        val sen = "불성실한 응답 적발 시, 패널 활동이 강제중지 되며 응답비는 지급되지 않습니다."

        val spannableString = SpannableString(sen)
        spannableString.setSpan(ForegroundColorSpan(Color.RED),21,26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textColor.text = spannableString


        registerAgree2.setOnClickListener {
            val intent_main : Intent = Intent(this, AddPanelInfoActivity::class.java)
            intent_main.putExtra("currentUser_login", currentUser)

            startActivity(intent_main)
            finish()
        }
    }
}