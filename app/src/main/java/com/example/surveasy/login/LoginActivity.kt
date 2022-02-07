package com.example.surveasy.login

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.surveasy.MainActivity
import com.example.surveasy.R
import com.example.surveasy.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth : FirebaseAuth
    val db = Firebase.firestore
    val loginInfoList = arrayListOf<LoginInfo>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ToolBar
        setSupportActionBar(binding.ToolbarLogin)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        binding.ToolbarLogin.setNavigationOnClickListener { onBackPressed() }


        // Login
        auth = FirebaseAuth.getInstance()
        binding.LoginBtn.setOnClickListener {
            login()
        }

    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null) {
            reload()
        }
    }

    private fun reload() {
        auth.currentUser!!.reload().addOnCompleteListener { task ->
            if(task.isSuccessful) {
                // updateUI(auth.currentUser)
                Log.d(ContentValues.TAG, "##### reload Success")
            }
            else {
                Log.e(ContentValues.TAG, "##### reload Fail", task.exception)
            }
        }
    }

    private fun login() {
        val loginEmail : String = binding.LoginInputEmail.text.toString()
        val loginPassword : String = binding.LoginInputPW.text.toString()

        if(loginEmail == "") {
            Toast.makeText(this@LoginActivity, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if(loginPassword == "") {
            Toast.makeText(this@LoginActivity, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else {
            auth.signInWithEmailAndPassword(loginEmail, loginPassword)
                .addOnCompleteListener(this) { task ->
                    if(task.isSuccessful) {
                        Log.d(TAG, "로그인 성공")
                        val user = auth.currentUser
                        // updateUI(user)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        Log.w(TAG, "로그인 실패", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        // updateUI(null)
                    }
                }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if(user != null) {
            val IdText: TextView = findViewById(R.id.Home_GreetingText)

        }
    }

}