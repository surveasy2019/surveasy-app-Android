package com.example.surveasy.login

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.surveasy.MainActivity
import com.example.surveasy.databinding.ActivityLoginBinding
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.example.surveasy.list.SurveyItems
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.user.UserApiClient


class LoginActivity : AppCompatActivity() {
    lateinit var mOAuthLoginModule: OAuthLogin
    private lateinit var binding: ActivityLoginBinding
    val db = Firebase.firestore
    val loginInfoList = arrayListOf<LoginInfo>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)


        setContentView(binding.root)

        binding.LoginBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        setSupportActionBar(binding.ToolbarLogin)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarLogin.setNavigationOnClickListener { onBackPressed() }


        binding.LoginKakao.setOnClickListener {
            UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->

                if (error != null) {
                    Toast.makeText(this, "로그인 실패 $error", Toast.LENGTH_LONG).show()
                    Log.i(TAG, "에러: $error")
                } else if (token != null) {

                }

                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.d(TAG, "fail")
                    } else if (user != null) {

                        val userInfo = hashMapOf(
                            "id" to user.id,
                            "name" to user.kakaoAccount?.profile?.nickname
                        )

                        db.collection("AppTestUser").document(user.id.toString())
                            .set(userInfo)

                        for(info in userInfo){
                            var uInfo : LoginInfo = LoginInfo(info.key[0] as Long, info.key[1] as String)
                            loginInfoList.add(uInfo)

                        }





                    }
                }


                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


        binding.buttonOAuthLoginImg.setOnClickListener {
            mOAuthLoginModule = OAuthLogin.getInstance()
            mOAuthLoginModule.init(
                this,
                "eo7WH6lwzklMcJwYNvcW",
                "Dg3dbXR8_B",
                "SURVEASY"
            )
            mOAuthLoginModule.startOauthLoginActivity(this, mOAuthLoginHandler);
        }
    }

    private val mOAuthLoginHandler: OAuthLoginHandler = object : OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if (success) {
                val accessToken: String = mOAuthLoginModule.getAccessToken(baseContext)
                val refreshToken: String = mOAuthLoginModule.getRefreshToken(baseContext)
                val expiresAt: Long = mOAuthLoginModule.getExpiresAt(baseContext)
                val tokenType: String = mOAuthLoginModule.getTokenType(baseContext)

                Log.i(TAG, "accessToken: $accessToken")
                Log.i(TAG, "refreshToken: $refreshToken")
                Log.i(TAG, "expiresAt: $expiresAt")
                Log.i(TAG, "tokenType: $tokenType")

            } else {
                val errorCode: String =
                    mOAuthLoginModule.getLastErrorCode(baseContext).code
                val errorDesc: String = mOAuthLoginModule.getLastErrorDesc(baseContext)
                Toast.makeText(
                    baseContext, "errorCode:" + errorCode
                            + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT
                ).show()


            }

        }
    }
}