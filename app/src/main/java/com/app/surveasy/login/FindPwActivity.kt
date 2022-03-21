package com.app.surveasy.login

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.app.surveasy.databinding.ActivityFindpwBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FindPwActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFindpwBinding
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFindpwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ToolBar
        setSupportActionBar(binding.ToolbarFindPw)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        binding.ToolbarFindPw.setNavigationOnClickListener { onBackPressed() }


        binding.FindPwBtn.setOnClickListener {
            findPw()
        }

    }

    private fun findPw() {
        val emailAddress = binding.FindPwInputEmail.text.toString()

        if(emailAddress == "") {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else {
            Firebase.auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        Log.d(TAG, "EEEEEEE Email sent.")
                        Toast.makeText(this@FindPwActivity, "비밀번호 재설정 이메일이 발송되었습니다.", Toast.LENGTH_SHORT).show()
                        intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        Toast.makeText(this@FindPwActivity, "해당 이메일로 가입된 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

        }

    }
}