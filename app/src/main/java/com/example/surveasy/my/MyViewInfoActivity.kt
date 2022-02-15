package com.example.surveasy.my

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import com.example.surveasy.databinding.ActivityMyviewinfoBinding
import com.example.surveasy.login.CurrentUserViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyViewInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyviewinfoBinding
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityMyviewinfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.ToolbarMyViewInfo)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        binding.ToolbarMyViewInfo.setNavigationOnClickListener {
            onBackPressed()
        }


        binding.MyViewInfoInfoItemName.text = "김ㅁㅁ"

    }
}