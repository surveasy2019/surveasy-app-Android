package com.beta.surveasy.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.beta.surveasy.R
import com.beta.surveasy.databinding.ActivityRegisterBinding


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

            supportActionBar?.setDisplayShowTitleEnabled(false)





        }
        binding.ToolbarRegister.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    fun fin(){
        finishAffinity()
    }

    fun toolbarHide(){
        supportActionBar?.hide()
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