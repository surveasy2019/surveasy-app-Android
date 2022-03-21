package com.app.surveasy.my.setting

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.surveasy.databinding.ActivityMyviewsettingpushBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class MyViewSettingPushActivity : AppCompatActivity() {
    val db = Firebase.firestore

    private lateinit var binding: ActivityMyviewsettingpushBinding

    override fun onStart() {
        super.onStart()
        binding = ActivityMyviewsettingpushBinding.inflate(layoutInflater)

        // Get pushOn from MyViewSettingActivity
        val pushOn = intent.getBooleanExtra("pushOn", false)
        if(pushOn == true) binding.MyViewSettingPushPushSwitch.isChecked = true

        // Get marketingAgree from MyViewSettingActivity
        val marketingAgree = intent.getBooleanExtra("marketingAgree", false)
        if(marketingAgree == true) binding.MyViewSettingPushMarketingSwitch.isChecked = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyviewsettingpushBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // Tool Bar
        setSupportActionBar(binding.ToolbarMyViewSettingPush)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        binding.ToolbarMyViewSettingPush.setNavigationOnClickListener {
            onBackPressed()
        }

        // Get pushOn from MyViewSettingActivity
        val pushOn = intent.getBooleanExtra("pushOn", false)
        if(pushOn == true) binding.MyViewSettingPushPushSwitch.isChecked = true

        // Get marketingAgree from MyViewSettingActivity
        val marketingAgree = intent.getBooleanExtra("marketingAgree", false)
        if(marketingAgree == true) binding.MyViewSettingPushMarketingSwitch.isChecked = true


        binding.MyViewSettingPushPushSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                db.collection("AndroidUser").document(Firebase.auth.currentUser!!.uid)
                    .update("pushOn", true)
                FirebaseMessaging.getInstance().subscribeToTopic("all").addOnSuccessListener {
                    Log.d(TAG, "*********** On SUccess")
                }

            }
            else {
                db.collection("AndroidUser").document(Firebase.auth.currentUser!!.uid)
                    .update("pushOn", false)
                FirebaseMessaging.getInstance().unsubscribeFromTopic("all").addOnSuccessListener {
                    Log.d(TAG, "*********** Off SUccess")
                }
            }
        }

        binding.MyViewSettingPushMarketingSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                db.collection("AndroidUser").document(Firebase.auth.currentUser!!.uid)
                    .update("marketingAgree", true)
            }
            else {
                db.collection("AndroidUser").document(Firebase.auth.currentUser!!.uid)
                    .update("marketingAgree", false)
            }
        }


    }
}