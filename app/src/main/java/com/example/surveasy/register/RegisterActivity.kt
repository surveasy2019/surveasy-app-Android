package com.example.surveasy.register

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.databinding.adapters.AutoCompleteTextViewBindingAdapter
import com.example.surveasy.R
import com.example.surveasy.databinding.ActivityRegisterBinding

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {


    private lateinit var binding: ActivityRegisterBinding
    val db = Firebase.firestore
    val registerModel by viewModels<RegisterInfo1ViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val transaction = supportFragmentManager.beginTransaction()
        setContentView(binding.root)
        transaction.add(R.id.RegisterView, RegisterAgree1Fragment()).commit()


        val registerToolbar : Toolbar? = findViewById(R.id.ToolbarRegister)
        setSupportActionBar(binding.ToolbarRegister)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            //supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setTitle("패널가입")




        }
        binding.ToolbarRegister.setNavigationOnClickListener {
            onBackPressed()
        }






    }

    fun goAgree2(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.RegisterView, RegisterAgree2Fragment())
            .commit()


    }


    fun goToRegister1() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.RegisterView, Register1Fragment())
            .commit()


    }


    fun goToRegister2() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.RegisterView, Register2Fragment())
            .commit()


    }

    fun goToRegisterFin() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.RegisterView, RegisterFinFragment())
            .commit()
    }
}