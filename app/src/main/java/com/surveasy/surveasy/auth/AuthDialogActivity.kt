package com.surveasy.surveasy.auth

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.surveasy.surveasy.databinding.ActivityAuthDialogBinding
import kotlinx.coroutines.launch

class AuthDialogActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAuthDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.authDialogOkBtn.setOnClickListener{
            lifecycleScope.launch {
                try {
                    this@AuthDialogActivity.let { UserApiClient.loginWithKakao(this@AuthDialogActivity) }

                    UserApiClient.instance.me { user, error ->
                        if (user != null) {
                            Log.d(ContentValues.TAG, "onCreateView: $$$$${user.id}")
                            val intent = Intent(this@AuthDialogActivity, AuthProcessActivity::class.java)
                            intent.putExtra("snsUid", user.id.toString())
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(this@AuthDialogActivity, "문제가 발생했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                        }

                    }

                }catch (error: Throwable) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        Log.d("MainActivity", "사용자가 취소")
                    } else {
                        Log.e("MainActivity", "인증 에러", error)
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}