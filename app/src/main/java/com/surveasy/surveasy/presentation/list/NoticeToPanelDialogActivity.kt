package com.surveasy.surveasy.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.surveasy.surveasy.databinding.ActivityNoticeToPanelDialogBinding

class NoticeToPanelDialogActivity : AppCompatActivity() {
    lateinit var binding : ActivityNoticeToPanelDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoticeToPanelDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val link = intent.getStringExtra("link")!!
        val id = intent.getIntExtra("id",0)!!
        val idChecked = intent.getIntExtra("idChecked",0)!!
        val index = intent.getIntExtra("index",0)!!
        val notice = intent.getStringExtra("notice")!!
        val reward = intent.getIntExtra("reward",0)
        val title : String = intent.getStringExtra("title")!!



        binding.NoticeToPanelText.text = notice

        //binding.NoticeToPanelCloseBtn.setOnClickListener { finish() }
        binding.NoticeToPanelOkBtn.setOnClickListener {
            val intent = Intent(this,SurveyListDetailActivity::class.java)
            intent.putExtra("link",link)
            intent.putExtra("id", id)
            intent.putExtra("idChecked", idChecked)
            intent.putExtra("index", index)
            intent.putExtra("reward",reward)
            intent.putExtra("title",title)
            startActivity(intent)
            finish()
        }

        binding.NoticeToPanelCloseBtn.setOnClickListener {
            onBackPressed()
        }
    }
}