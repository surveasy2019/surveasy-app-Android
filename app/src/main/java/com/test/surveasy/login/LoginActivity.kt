package com.test.surveasy.login

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.test.surveasy.MainActivity
import com.test.surveasy.databinding.ActivityLoginBinding
import com.test.surveasy.list.UserSurveyItem
import com.test.surveasy.register.RegisterActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth : FirebaseAuth
    val db = Firebase.firestore
    val userModel by viewModels<CurrentUserViewModel>()
    var autoLogin: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ToolBar
//        setSupportActionBar(binding.ToolbarLogin)
//        if (supportActionBar != null) {
//            supportActionBar?.setDisplayHomeAsUpEnabled(true)
//            supportActionBar?.setDisplayShowTitleEnabled(false)
//        }
//        binding.ToolbarLogin.setNavigationOnClickListener { onBackPressed() }


        // Auto Login Checkbox
        binding.LoginAutoLogin.setOnCheckedChangeListener { button, isChecked ->
            autoLogin = button.isChecked
            Log.d(TAG, "AUTOOOOOOOOOOOOO  $autoLogin")
        }

        // Login
        auth = FirebaseAuth.getInstance()
        binding.LoginBtn.setOnClickListener {
            login()
        }

        // Go to RegisterActivity
        binding.LoginRegister.setOnClickListener {
            intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Go to FindPwActivity
        binding.LoginFindPw.setOnClickListener {
            intent = Intent(this, FindPwActivity::class.java)
            startActivity(intent)
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
                Log.d(ContentValues.TAG, "##### reload Success $task")
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
                        docRef.update("autoLogin", autoLogin)

                        val userSurveyList = ArrayList<UserSurveyItem>()

                        docRef.collection("UserSurveyList").get()
                            .addOnSuccessListener { documents ->
                                for(document in documents){
                                    var item : UserSurveyItem = UserSurveyItem(
                                        Integer.parseInt(document["lastIDChecked"].toString()) as Int,
                                        document["title"] as String?,
                                        Integer.parseInt(document["panelReward"].toString()) as Int?,
                                        document["responseDate"] as String?,
                                        document["isSent"] as Boolean
                                    )
                                    userSurveyList.add(item)

                                }
                            }

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
                                    snapshot.result["accountNumber"].toString(),
                                    snapshot.result["accountOwner"].toString(),
                                    snapshot.result["inflowPath"].toString(),
                                    snapshot.result["didFirstSurvey"] as Boolean,
                                    snapshot.result["autoLogin"] as Boolean,
                                    Integer.parseInt(snapshot.result["reward_current"].toString()),
                                    Integer.parseInt(snapshot.result["reward_total"].toString()),
                                    snapshot.result["marketingAgree"] as Boolean,
                                    userSurveyList
                                )
                                userModel.currentUser = currentUser
                                Log.d(TAG, "GGGGGGGG fetch fun 내부 userModel: ${userModel.currentUser.didFirstSurvey}")
                                Log.d(TAG, "FFFFFFFF fetch fun 내부 userModel: ${userModel.currentUser.UserSurveyList.toString()}")


                                //로그인 한 모든사람에게 알림 전송
                                FirebaseMessaging.getInstance().subscribeToTopic("all")

                                val intent_main : Intent = Intent(this, MainActivity::class.java)
                                intent_main.putExtra("currentUser_login", currentUser)
                                startActivity(intent_main)
                                finishAffinity()
                            }

                        }
                        //updateUI(user)
                        updateFcmToken(uid)
                    }
                    else {
                        //Log.w(TAG, "@@@@@@@@@@@@@ 로그인 실패  ${task.exception} 끝")

                        if(task.exception.toString() == "com.google.firebase.auth.FirebaseAuthInvalidUserException: There is no user record corresponding to this identifier. The user may have been deleted.") {
                            Toast.makeText(baseContext, "가입되지 않은 이메일 주소입니다.", Toast.LENGTH_SHORT).show()
                        }
                        if(task.exception.toString() == "com.google.firebase.auth.FirebaseAuthInvalidCredentialsException: The password is invalid or the user does not have a password.") {
                            Toast.makeText(baseContext, "비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                        }
                        if(task.exception.toString() == "com.google.firebase.auth.FirebaseAuthInvalidCredentialsException: The email address is badly formatted.") {
                            Toast.makeText(baseContext, "유효하지 않은 이메일 형식입니다.", Toast.LENGTH_SHORT).show()
                        }
//
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