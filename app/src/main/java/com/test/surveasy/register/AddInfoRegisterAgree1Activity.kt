package com.test.surveasy.register

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.test.surveasy.R
import com.test.surveasy.login.CurrentUser

class AddInfoRegisterAgree1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_panel_agree1)

        Firebase.auth.signOut()
        Log.d(ContentValues.TAG, "((((((((((( logout : ${Firebase.auth.currentUser?.uid}")

        val currentUser = intent.getParcelableExtra<CurrentUser>("currentUser_login")!!
        val agreeAll : CheckBox = findViewById(R.id.registerAgree1_agree11)
        val agree1 : CheckBox = findViewById(R.id.registerAgree1_agree22)
        val term1 : TextView = findViewById(R.id.register_goTerm1)
        val agree2 : CheckBox = findViewById(R.id.registerAgree1_agree33)
        val term2 : TextView = findViewById(R.id.register_goTerm2)
        val agree3 : CheckBox = findViewById(R.id.registerAgree1_agree44)
        val registerAgree1 : Button = findViewById(R.id.RegisterAgree1_Btn1)
        val text : TextView = findViewById(R.id.SNSAgree_text)

        registerAgree1.setOnClickListener {
            if(agree1.isChecked && agree2.isChecked){
                Log.d(ContentValues.TAG, currentUser.name.toString())
                if(agree3.isChecked){
                    currentUser.marketingAgree = true
                    val intent_main : Intent = Intent(this, AddInfoRegisterAgree2Activity::class.java)
                    intent_main.putExtra("currentUser_login", currentUser)
                    startActivity(intent_main)
                    finish()
                }else{
                    val intent_main : Intent = Intent(this, AddInfoRegisterAgree2Activity::class.java)
                    intent_main.putExtra("currentUser_login", currentUser)
                    startActivity(intent_main)
                    finish()
                }

            }else{
                Toast.makeText(this,"필수 항목에 동의해주세요", Toast.LENGTH_LONG).show()
            }

        }

        term1.setOnClickListener {
            val intent = Intent(this,RegisterTerm1::class.java)
            startActivity(intent)
        }
        term2.setOnClickListener {
            val intent = Intent(this,RegisterTerm2::class.java)
            startActivity(intent)
        }




        agreeAll.setOnClickListener { view ->
            if(agreeAll.isChecked){
                agree1.isChecked = true
                agree2.isChecked = true
                agree3.isChecked = true
            }else{
                agree1.isChecked = false
                agree2.isChecked = false
                agree3.isChecked = false
            }
            if(agree3.isChecked){
                text.text ="할인 쿠폰 및 혜택, 이벤트 등 유익한 정보를 SMS나\n" +
                        "이메일로 받아보실 수 있습니다."
            }else{
                text.text=""
            }

        }
        agree1.setOnClickListener {
            if(!agree1.isChecked||!agree2.isChecked||!agree3.isChecked){
                agreeAll.isChecked=false
            }
            if(agree1.isChecked && agree2.isChecked && agree3.isChecked){
                agreeAll.isChecked=true
            }
        }
        agree2.setOnClickListener {
            if(!agree1.isChecked||!agree2.isChecked||!agree3.isChecked){
                agreeAll.isChecked=false
            }
            if(agree1.isChecked && agree2.isChecked && agree3.isChecked){
                agreeAll.isChecked=true
            }
        }
        agree3.setOnClickListener {
            if(!agree1.isChecked||!agree2.isChecked||!agree3.isChecked){
                agreeAll.isChecked=false
            }
            if(agree1.isChecked && agree2.isChecked && agree3.isChecked){
                agreeAll.isChecked=true
            }
            if(agree3.isChecked){
                text.text ="할인 쿠폰 및 혜택, 이벤트 등 유익한 정보를 SMS나\n" +
                        "이메일로 받아보실 수 있습니다."
            }else{
                text.text=""
            }
        }
    }
}