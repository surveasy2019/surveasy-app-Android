package com.surveasy.surveasy.auth

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.surveasy.surveasy.MainRepository
import com.surveasy.surveasy.MainViewModel
import com.surveasy.surveasy.MainViewModelFactory
import com.surveasy.surveasy.databinding.ActivityAuthProcessBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthProcessActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAuthProcessBinding
    private lateinit var snsUid : String
    private val db = Firebase.firestore
    private var check = false
    private var done = false
    private lateinit var authViewModel: AuthViewModel
    private lateinit var authViewModelFactory: AuthViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthProcessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        snsUid = intent.getStringExtra("snsUid").toString()

        authViewModelFactory = AuthViewModelFactory(AuthRepository())
        authViewModel = ViewModelProvider(this, authViewModelFactory)[AuthViewModel::class.java]

        CoroutineScope(Dispatchers.Main).launch { checkAuthWithFB(snsUid) }

        if(!done){
            binding.authDialogText.text = "본인 확인 처리 중 ..."
            binding.authDoneBtn.visibility = View.INVISIBLE
        }else{
            binding.authDialogText.text = "본인 확인이 완료되었습니다."
            binding.authDoneBtn.visibility = View.VISIBLE
        }
    }

    private suspend fun checkAuthWithFB(id : String){
        val uid = Firebase.auth.uid.toString()
        CoroutineScope(Dispatchers.Main).launch {
            authViewModel.checkAuthWithFB(uid, id)
            authViewModel.repositories1.observe(this@AuthProcessActivity){
                if(it.check==true) {
                    invalidAuth()
                }else {
                    CoroutineScope(Dispatchers.Main).launch {
                        authViewModel.updateAuthStatus(uid, id)
                        authViewModel.repositories2.observe(this@AuthProcessActivity) {
                            Log.d(TAG, "checkAuthWithFB: viewModel observe")
                            if (it.check == true) {
                                binding.authDialogText.text = "본인 확인이 완료되었습니다."
                                binding.authDoneBtn.visibility = View.VISIBLE
                                binding.authDoneBtn.text = "설문 참여하러 가기"

                                binding.authDoneBtn.setOnClickListener {
                                    finish()
                                }
                            }
                        }
                    }
                }
            }



        }
    }

    override fun onBackPressed() {
    }

    private fun invalidAuth(){
        binding.authDialogText.text = "다른 계정에서 이미 인증된 사용자입니다."
        binding.authDoneBtn.visibility = View.VISIBLE
        binding.authDoneBtn.text = "카카오 채널 문의하기"
        binding.authDoneBtn.setOnClickListener{
            val url = "http://pf.kakao.com/_exfmwb"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
    }
}