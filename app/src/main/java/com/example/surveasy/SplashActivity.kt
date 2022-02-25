package com.example.surveasy

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.surveasy.firstIntroduceScreen.FirstIntroduceScreenActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import java.time.Duration

class SplashActivity : AppCompatActivity() {
    val db = Firebase.firestore
    var token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({

            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

            finish()
        },3000)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(
                        ContentValues.TAG,
                        "Fetching FCM registration token failed",
                        task.exception
                    )
                    return@OnCompleteListener
                }
                token = task.result

            })
        db.collection("AndroidFirstScreen").get()
            .addOnSuccessListener { result ->
                var i =0
                for(document in result){
                    if(document.id == token){
                        i++
                    }
                }
                if(i==0){
                    startActivity(Intent(this,FirstIntroduceScreenActivity::class.java))
                }else{
                    startActivity(Intent(this,MainActivity::class.java))
                }
            }
}
}