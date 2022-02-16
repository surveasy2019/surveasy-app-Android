package com.example.surveasy.my.setting

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.surveasy.databinding.ActivityMyviewsettingwithdrawBinding
import com.example.surveasy.login.LoginActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MyViewSettingWithdrawActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyviewsettingwithdrawBinding
    private lateinit var builder : AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyviewsettingwithdrawBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.ToolbarMyViewSettingWithdraw)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarMyViewSettingWithdraw.setNavigationOnClickListener {
            onBackPressed()
        }

        builder = AlertDialog.Builder(this)
        binding.MyViewSettingWithdrawWithdrawBtn.setOnClickListener{
            builder.setTitle("회원탈퇴하시겠습니까?")
                .setCancelable(true)
                .setPositiveButton("예"){ dialogInterface, it ->
                    finish()
                    val user = Firebase.auth.currentUser!!
                    user.delete()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "########User account deleted.")
                            }
                        }

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton("아니요"){ dialogInterface, it ->
                    dialogInterface.cancel()
                }
                .show()

        }

    }
}