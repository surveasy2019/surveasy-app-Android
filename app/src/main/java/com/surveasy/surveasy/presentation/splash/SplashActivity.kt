package com.surveasy.surveasy.presentation.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.surveasy.surveasy.R

class SplashActivity : AppCompatActivity() {
    var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


//        // Initialization of Amplitude
//        val client = Amplitude.getInstance()
//            .initialize(getApplicationContext(), "ae22fbd62558adb236f993284cc62c71")
//            .enableForegroundTracking(application)
//
//
//        if(isConnectInternet() != "null"){
//            val handler = Handler(Looper.getMainLooper())
//            handler.postDelayed({
//
//                finish()
//
//            },4000)
//        }else{
//            val handler = Handler(Looper.getMainLooper())
////            handler.postDelayed({
////                val intent = Intent(baseContext, NetworkAlertActivity::class.java)
////                startActivity(intent)
////                finish()
//
//            //},4000)
//        }
//
//
//
//        supportActionBar?.hide()
//
//        FirebaseMessaging.getInstance().token.addOnCompleteListener(
//            OnCompleteListener { task ->
//                if (!task.isSuccessful) {
//                    Log.w(
//                        ContentValues.TAG,
//                        "Fetching FCM registration token failed",
//                        task.exception
//                    )
//                    return@OnCompleteListener
//                }
//                token = task.result
//
//            })
//
//
//        db.collection("AndroidFirstScreen").get()
//            .addOnSuccessListener { result ->
//                nextActivity()
////                var i = 0
////                for(document in result){   if(document.id == token) i++   }
////                if(i==0){
////                    startActivity(Intent(this,FirstIntroduceScreenActivity::class.java))
////                    finish()
////                }
////                else {
////                    nextActivity()
////
//////                    var bannerIndex = 0
//////                    for(item in bannerModel.uriList) {
//////                        intent.putExtra(item, "banner" + bannerIndex.toString())
//////                        bannerIndex++
//////                    }
////                }
//            }

    }


//




}