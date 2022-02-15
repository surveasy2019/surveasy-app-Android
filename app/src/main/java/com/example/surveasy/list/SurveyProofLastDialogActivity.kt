package com.example.surveasy.list

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.surveasy.MainActivity
import com.example.surveasy.R
import com.example.surveasy.databinding.ActivitySurveyprooflastdialogBinding
import com.example.surveasy.login.CurrentUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SurveyProofLastDialogActivity : AppCompatActivity() {
    val db = Firebase.firestore


    private lateinit var binding: ActivitySurveyprooflastdialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySurveyprooflastdialogBinding.inflate(layoutInflater)

        setContentView(binding.root)



        setSupportActionBar(binding.ToolbarSurveyListDetailResponseProof)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarSurveyListDetailResponseProof.setNavigationOnClickListener {
            onBackPressed()
        }



        binding.SurveyListDetailResponseProofBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)


        }


    }
}




