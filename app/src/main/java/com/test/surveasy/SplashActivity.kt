package com.test.surveasy

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.MediaController
import android.widget.VideoView
import com.bumptech.glide.Glide
import com.test.surveasy.firstIntroduceScreen.FirstIntroduceScreenActivity
import com.test.surveasy.home.NetworkAlertActivity
import com.test.surveasy.login.LoginActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    val db = Firebase.firestore
    var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Glide.with(this).load(R.raw.splash_fast).override(1200).into(findViewById(R.id.splash_gif))


        if(isConnectInternet() != "null"){
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({

                finish()

            },4000)
        }else{
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                val intent = Intent(baseContext, NetworkAlertActivity::class.java)
                startActivity(intent)
                finish()

            },4000)
        }


//        Handler().postDelayed({
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//
//            finish()
//        },3000)

        supportActionBar?.hide()




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
                Log.d(TAG, "$$$$$$$$$$$$$$$$$ $token")

            })
        db.collection("AndroidFirstScreen").get()
            .addOnSuccessListener { result ->
                var i = 0
                for(document in result){   if(document.id == token) i++   }
                if(i==0){
                    startActivity(Intent(this,FirstIntroduceScreenActivity::class.java))
                }
                else {
                    nextActivity()

//                    var bannerIndex = 0
//                    for(item in bannerModel.uriList) {
//                        intent.putExtra(item, "banner" + bannerIndex.toString())
//                        bannerIndex++
//                    }
                }
            }
    }

    private fun isConnectInternet() : String {
        val cm : ConnectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo : NetworkInfo? = cm.activeNetworkInfo
        return networkInfo.toString()
    }

    private fun nextActivity() {

        // [1] 현재 Firebase auth CurrentUser 존재하지 않는 경우 (로그아웃 상태)
        if (Firebase.auth.currentUser == null) {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        // [2] 현재 Firebase auth CurrentUser 존재하는 경우 (로그인 상태)
        else {

            // CurrentUser의 autoLogin 상태 확인
            db.collection("AndroidUser").document(Firebase.auth.currentUser!!.uid)
                .get().addOnSuccessListener { snapshot ->
                    if (snapshot["autoLogin"] == false) {
                        intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
        }
    }

}