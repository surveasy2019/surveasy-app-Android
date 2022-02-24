package com.example.surveasy.my.setting

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.surveasy.databinding.ActivityMyviewsettingpushBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class MyViewSettingPushActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyviewsettingpushBinding
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

        val db = Firebase.firestore
        var pushOn : Boolean? = null
        db.collection("AndroidUser").document(Firebase.auth.currentUser!!.uid)
            .get().addOnSuccessListener { result ->
                pushOn = result["pushOn"] as Boolean
                Log.d(TAG, "PPPPPPPPPPPPPP : $pushOn")
                if(pushOn == true) binding.MyViewSettingPushPushSwitch.isChecked = true
            }


        binding.MyViewSettingPushPushSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                FirebaseMessaging.getInstance().subscribeToTopic("all").addOnSuccessListener {
                    Log.d(TAG, "*********** On SUccess")
                }

            }
            else {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("all").addOnSuccessListener {
                    Log.d(TAG, "*********** Off SUccess")
                }
            }
        }


    }
}