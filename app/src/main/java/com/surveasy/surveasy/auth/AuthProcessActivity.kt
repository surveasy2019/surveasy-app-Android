package com.surveasy.surveasy.auth

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthProcessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        snsUid = intent.getStringExtra("snsUid").toString()
        checkAuthWithFB(snsUid)
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
                }else{
                    val map = hashMapOf("fbUid" to Firebase.auth.uid.toString(), "snsUid" to id.toString())
                    db.collection("panelData").document(Firebase.auth.uid.toString())
                       .update("snsAuth", true, "snsUid", id)
                    db.collection("snsAuthCheck").document(id.toString()).set(map)
                }
            }




//        db.collection("panelData").document(Firebase.auth.uid.toString())
//            .update("SNSAuth", true, "SNSUid", id)


    }
}