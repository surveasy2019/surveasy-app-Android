package com.example.surveasy.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.surveasy.R
import com.example.surveasy.databinding.ActivityRegister1Binding
import com.example.surveasy.home.HomeFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class Register1Activity: AppCompatActivity() {

    private lateinit var binding : ActivityRegister1Binding
    private lateinit var auth : FirebaseAuth
    private var firebaseUserID: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        binding = ActivityRegister1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        setSupportActionBar(binding.ToolbarRegister)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        binding.ToolbarRegister.setNavigationOnClickListener { onBackPressed() }



        // Register
        auth = FirebaseAuth.getInstance()
        binding.RegisterFinBtn.setOnClickListener {
            register()
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
                Log.d(TAG, "##### reload Success")
            }
            else {
                Log.e(TAG, "##### reload Fail", task.exception)
            }
        }
    }


    private fun register() {
        val registerEmail: String = binding.RegisterEmailInput.text.toString()
        val registerPassword: String = binding.RegisterPwInput.text.toString()
        val registerPasswordCheck: String = binding.RegisterPwCheckInput.text.toString()
        val registerName : String = binding.RegisterNameInput.text.toString()

        if(registerEmail == "") {
            Toast.makeText(this@Register1Activity, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if(registerPassword == "") {
            Toast.makeText(this@Register1Activity, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if(registerPassword != registerPasswordCheck) {
            Toast.makeText(this@Register1Activity, "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if(registerName == "") {
            Toast.makeText(this@Register1Activity, "성함을 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else {
            val db = Firebase.firestore

            auth.createUserWithEmailAndPassword(registerEmail, registerPassword)
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful) {
                        firebaseUserID = auth.currentUser!!.uid

                        FirebaseMessaging.getInstance().token.addOnCompleteListener(
                            OnCompleteListener { task ->
                                if(!task.isSuccessful) {
                                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                                    return@OnCompleteListener
                                }
                                val token = task.result

                                val user = hashMapOf(
                                    "email" to registerEmail,
                                    "uid" to firebaseUserID,
                                    "fcmToken" to token,
                                    "name" to registerName,
                                    "firstSurvey" to false
                                )
                                Log.d(TAG, "#####UID : $firebaseUserID")

                                db.collection("AndroidUser").document(firebaseUserID)
                                    .set(user)
                                    .addOnSuccessListener { documentReference ->
                                        Log.d(TAG, "#####DocumentSnapshot added")

                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(TAG, "##### ERROR adding document", e)
                                    }


                            })
                        val intent : Intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        Toast.makeText(baseContext, "#####Auth Error: " + task.exception!!.message.toString()
                            , Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}