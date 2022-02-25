package com.example.surveasy.my.setting

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.surveasy.R
import com.example.surveasy.databinding.ActivityMyviewsettingBinding
import com.example.surveasy.login.LoginActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyViewSettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyviewsettingBinding
    private lateinit var builder : AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyviewsettingBinding.inflate(layoutInflater)

        setContentView(binding.root)


        val db = Firebase.firestore
        var pushOn : Boolean? = null
        db.collection("AndroidUser").document(Firebase.auth.currentUser!!.uid)
            .get().addOnSuccessListener { result ->
                pushOn = result["pushOn"] as Boolean
                Log.d(TAG, "PPPPPPPPPPPPPP : $pushOn")
            }

        binding.MyViewSettingPush.setOnClickListener {
            val intent = Intent(this, MyViewSettingPushActivity::class.java)
            intent.putExtra("pushOn", pushOn)
            startActivity(intent)
        }

        binding.MyViewSettingVersion.setOnClickListener {
            val intent = Intent(this, MyViewSettingVersionActivity::class.java)
            startActivity(intent)
        }

        binding.MyViewSettingWithdraw.setOnClickListener {
            val intent = Intent(this, MyViewSettingWithdrawActivity::class.java)
            startActivity(intent)
        }

        builder = AlertDialog.Builder(this)
        binding.MyViewSettingLogout.setOnClickListener {
            builder.setTitle("로그아웃 하시겠습니까?")
                .setCancelable(true)
                .setPositiveButton("예"){ dialogInterface, it ->
                    finish()
                    Firebase.auth.signOut()
                    Log.d(TAG, "((((((((((( logout : ${Firebase.auth.currentUser?.uid}")

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton("아니요"){ dialogInterface, it ->
                    dialogInterface.cancel()
                }
                .show()
        }

        setSupportActionBar(binding.ToolbarMyViewSetting)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarMyViewSetting.setNavigationOnClickListener {
            onBackPressed()
        }




    }
}