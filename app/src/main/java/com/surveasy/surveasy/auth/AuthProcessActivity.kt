package com.surveasy.surveasy.auth

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.surveasy.surveasy.databinding.ActivityAuthProcessBinding

class AuthProcessActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAuthProcessBinding
    private lateinit var snsUid : String
    private val db = Firebase.firestore
    private var check = false
    private var done = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthProcessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        snsUid = intent.getStringExtra("snsUid").toString()
        checkAuthWithFB(snsUid)
        if(!done){
            binding.authDialogText.text = "본인 확인 처리 중 ..."
            binding.authDoneBtn.visibility = View.INVISIBLE
        }else{
            binding.authDialogText.text = "본인 확인이 완료되었습니다."
            binding.authDoneBtn.visibility = View.VISIBLE
        }
    }

    private fun checkAuthWithFB(id : String){
        db.collection("snsAuthCheck").whereEqualTo("fbUid", Firebase.auth.uid.toString())
            .get()
            .addOnSuccessListener { snapshot ->
                for(res in snapshot){
                   if(res.id == id) check = true
                }

                if(check){
                    Toast.makeText(this, "이미 존재하는 사용자", Toast.LENGTH_SHORT).show()
                    invalidAuth()
                }else{
                    val map = hashMapOf("fbUid" to Firebase.auth.uid.toString(), "snsUid" to id.toString())
                    db.collection("panelData").document(Firebase.auth.uid.toString())
                       .update("snsAuth", true, "snsUid", id)
                    db.collection("snsAuthCheck").document(id.toString()).set(map)
                        .addOnCompleteListener {
                            binding.authDialogText.text = "본인 확인이 완료되었습니다."
                            binding.authDoneBtn.visibility = View.VISIBLE
                        }
                }
            }
//        db.collection("panelData").document(Firebase.auth.uid.toString())
//            .update("SNSAuth", true, "SNSUid", id)


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