package com.example.surveasy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.example.surveasy.databinding.ActivityRegisterBinding
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

            val input = hashMapOf(
                "name" to binding.RegisterInputName.text.toString(),
                "recommend" to binding.RegisterInputRecommend.text.toString()
            )

            db.collection("AppTest1").document(binding.RegisterInputName.text.toString())
                .set(input)

            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
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