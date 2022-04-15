package com.beta.surveasy.my

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.beta.surveasy.databinding.ActivityMyviewcontactBinding

class MyViewContactActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMyviewcontactBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyviewcontactBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.ToolbarMyViewContact)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarMyViewContact.setNavigationOnClickListener { onBackPressed()  }

        binding.MyViewContactKakaoTalk.setOnClickListener {
            val url = "http://pf.kakao.com/_exfmwb"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        binding.MyViewContactEmail.setOnClickListener {
            val textToCopy = "surveasy2019@yonsei.ac.kr"
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text",textToCopy)
            clipboardManager.setPrimaryClip(clipData)

            Toast.makeText(this,"이메일 주소가 복사되었습니다", Toast.LENGTH_LONG).show()

        }

    }
}