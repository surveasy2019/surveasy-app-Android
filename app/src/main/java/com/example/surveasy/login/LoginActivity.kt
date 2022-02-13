package com.example.surveasy.login

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.surveasy.MainActivity
import com.example.surveasy.R
import com.example.surveasy.databinding.ActivityLoginBinding
import com.example.surveasy.home.HomeFragment
import com.example.surveasy.list.SurveyListFirstSurveyActivity
import com.example.surveasy.list.UserSurveyItem
import com.example.surveasy.list.UserSurveyList
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth : FirebaseAuth
    val db = Firebase.firestore
    val userModel by viewModels<CurrentUserViewModel>()

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

        // Go to Register1Activity
        binding.LoginRegister.setOnClickListener{

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
                        val uid = user!!.uid.toString()

                        val docRef = db.collection("AndroidUser").document(uid)
                        docRef.get().addOnCompleteListener { snapshot ->
                            if(snapshot != null) {
                                val currentUser : CurrentUser = CurrentUser(
                                    snapshot.result["uid"].toString(),
                                    snapshot.result["fcmToken"].toString(),
                                    snapshot.result["name"].toString(),
                                    snapshot.result["email"].toString(),
                                    snapshot.result["phone"].toString(),
                                    snapshot.result["gender"].toString(),
                                    snapshot.result["birthDate"].toString(),
                                    snapshot.result["accountType"].toString(),
                                    snapshot.result["accountNumber"] as Int,
                                    snapshot.result["accountOwner"].toString(),
                                    snapshot.result["inflowPath"].toString(),
                                    snapshot.result["firstSurvey"] as Boolean?,
                                    )
                                userModel.currentUser = currentUser

                                val userSurveyList : UserSurveyItem = UserSurveyItem(
                                    snapshot.result["reward"] as Int?,
                                    snapshot.result["id"] as String?,
                                    snapshot.result["responseDate"] as String?,
                                    snapshot.result["isSent"] as Boolean?
                                )

                                //로그인 한 모든사람에게 알림 전송
                                FirebaseMessaging.getInstance().subscribeToTopic("all")

                                val intent_main : Intent = Intent(this, MainActivity::class.java)
                                intent_main.putExtra("currentUser_login", currentUser)
                                startActivity(intent_main)
                            }

                        }
                        //updateUI(user)
                        updateFcmToken(uid)
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

    private fun updateFcmToken(uid: String) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if(!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            val db = Firebase.firestore
            val docRef = db.collection("AndroidUser").document(uid)
            docRef.update("fcmToken", token)
        })
    }


}