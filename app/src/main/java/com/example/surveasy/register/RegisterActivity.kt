package com.example.surveasy.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import com.example.surveasy.R
import com.example.surveasy.databinding.ActivityRegisterBinding
import com.example.surveasy.login.CurrentUserViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    var i = 0

    private lateinit var binding:ActivityRegisterBinding
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userModel by viewModels<CurrentUserViewModel>()

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
        binding.RegisterAgreeBtn.text = "다음"

        binding.RegisterAgreeBtn.setOnClickListener {
            when(i){
                0 -> goAgree2()
                1 -> goToRegister1()
                2 -> goToRegister2()
                3 -> goToRegisterFin()

            }
        }



    }

    fun goAgree2(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.RegisterView, RegisterAgree2Fragment())
            .commit()
        i=1
        binding.RegisterAgreeBtn.text = "확인했습니다"
    }


    fun goToRegister1() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.RegisterView, Register1Fragment())
            .commit()
        i=2
        binding.RegisterAgreeBtn.text = "다음"
    }

    fun goToRegister2() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.RegisterView, Register2Fragment())
            .commit()
        i=3
       binding.RegisterAgreeBtn.text = "패널 가입 완료"

    }

    fun goToRegisterFin() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.RegisterView, RegisterFinFragment())
            .commit()
    }
}