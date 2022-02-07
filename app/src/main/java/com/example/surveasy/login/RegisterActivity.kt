package com.example.surveasy.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import com.example.surveasy.R
import com.example.surveasy.databinding.ActivityRegisterBinding
import com.example.surveasy.list.SurveyItems
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRegisterBinding
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.RegisterBtn.setOnClickListener {
//
//            val input = hashMapOf(
//                "name" to binding.RegisterInputName.text.toString(),
//                "recommend" to binding.RegisterInputRecommend.text.toString()
//            )
//
//            db.collection("AppTest1").document(binding.RegisterInputName.text.toString())
//                .set(input)
//
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)




        }

        val registerToolbar : Toolbar? = findViewById(R.id.ToolbarRegister)
        setSupportActionBar(binding.ToolbarRegister)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarRegister.setNavigationOnClickListener {
            onBackPressed()
        }







    }
}