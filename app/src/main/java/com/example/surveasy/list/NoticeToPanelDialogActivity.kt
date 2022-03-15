package com.example.surveasy.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.surveasy.R
import com.example.surveasy.databinding.ActivityNoticeToPanelDialogBinding

class NoticeToPanelDialogActivity : AppCompatActivity() {
    lateinit var binding : ActivityNoticeToPanelDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoticeToPanelDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val link = intent.getStringExtra("link")!!
        val id = intent.getStringExtra("id")!!
        val index = intent.getIntExtra("index",0)!!
        val notice = intent.getStringExtra("notice")!!



        binding.NoticeToPanelText.text = notice

        //binding.NoticeToPanelCloseBtn.setOnClickListener { finish() }
        binding.NoticeToPanelOkBtn.setOnClickListener {
            val intent = Intent(this,SurveyListDetailActivity::class.java)
            intent.putExtra("link",link)
            intent.putExtra("id", id)
            intent.putExtra("index", index)
            startActivity(intent)
            finish()
        }
    }
}