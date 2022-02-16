package com.example.surveasy.my

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.surveasy.R
import com.example.surveasy.databinding.ActivityMyviewinfoBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyViewInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyviewinfoBinding
    val db = Firebase.firestore
    val infoDataModel by viewModels<InfoDataViewModel>()

    var fragment : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyviewinfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.ToolbarMyViewInfo)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        binding.ToolbarMyViewInfo.setNavigationOnClickListener {
            onBackPressed()
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.MyViewInfo_View, MyViewInfo1Fragment()).commit()


        fetchInfoData()


        binding.MyViewInfoEditBtn.setOnClickListener{
            if(fragment == 1) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.MyViewInfo_View, MyViewInfo2Fragment()).commit()
                fragment = 2
                binding.MyViewInfoEditBtn.text = "수정완료"
            }
            else if(fragment == 2) {
                updateInfo()
                fetchInfoData()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.MyViewInfo_View, MyViewInfo1Fragment()).commit()
                fragment = 1
                binding.MyViewInfoEditBtn.text = "수정하기"
            }
        }


    }

    fun fetchInfoData() {

        val docRef = db.collection("AndroidUser").document(Firebase.auth.currentUser!!.uid)
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val infoData: InfoData = InfoData(
                    document["name"] as String,
                    document["birthDate"] as String,
                    document["gender"] as String,
                    document["email"] as String,
                    document["phoneNumber"] as String,
                    document["accountType"] as String,
                    document["accountNumber"] as String,
                    null
                )
                Log.d(TAG, "*********** ${infoDataModel.infoData.birthDate}")
                infoDataModel.infoData = infoData
            }
        }

        docRef.collection("FirstSurvey").document(Firebase.auth.currentUser!!.uid)
            .get().addOnSuccessListener { document ->
                if (document != null) {
                    infoDataModel.infoData.EngSurvey = document["EngSurvey"] as Boolean
                    setInfo()
                }
            }
    }


    fun setInfo() {
        val name = findViewById<TextView>(R.id.MyViewInfo_InfoItem_Name)
        val birthDate = findViewById<TextView>(R.id.MyViewInfo_InfoItem_BirthDate)
        val gender = findViewById<TextView>(R.id.MyViewInfo_InfoItem_Gender)
        val email = findViewById<TextView>(R.id.MyViewInfo_InfoItem_Email)
        val phoneNumber = findViewById<TextView>(R.id.MyViewInfo_InfoItem_PhoneNumber)
        val accountType = findViewById<TextView>(R.id.MyViewInfo_InfoItem_AccountType)
        val accountNumber = findViewById<TextView>(R.id.MyViewInfo_InfoItem_AccountNumber)
        val EngSurvey = findViewById<TextView>(R.id.MyViewInfo_InfoItem_EngSurvey)
        val EngSurveyRadioGroup = findViewById<RadioGroup>(R.id.MyViewInfo_InfoItem_EngSurveyRadioGroup)
        val EngSurveyTrue = findViewById<RadioButton>(R.id.MyViewInfo_InfoItem_EngSurvey_O)
        val EngSurveyFalse = findViewById<RadioButton>(R.id.MyViewInfo_InfoItem_EngSurvey_X)

        name.text = infoDataModel.infoData.name
        birthDate.text = infoDataModel.infoData.birthDate
        gender.text = infoDataModel.infoData.gender
        email.text = infoDataModel.infoData.email
        phoneNumber.text = infoDataModel.infoData.phoneNumber
        accountType.text = infoDataModel.infoData.accountType
        accountNumber.text = infoDataModel.infoData.accountNumber
        if(infoDataModel.infoData.EngSurvey == true) {
            EngSurvey.text = "희망함"
        }
        else if(infoDataModel.infoData.EngSurvey == false) {
            EngSurvey.text = "희망하지 않음"
        }

    }

    fun updateInfo() {
        val docRef = db.collection("AndroidUser").document(Firebase.auth.currentUser!!.uid)

        val phoneNumberEdit = findViewById<EditText>(R.id.MyViewInfo_InfoItem_PhoneNumberEdit)
        val accountNumberEdit = findViewById<EditText>(R.id.MyViewInfo_InfoItem_AccountNumberEdit)

        if(phoneNumberEdit.text.toString().trim().isNotEmpty()) {
            infoDataModel.infoData.phoneNumber = phoneNumberEdit.text.toString()
        }
        if(accountNumberEdit.text.toString().trim().isNotEmpty()) {
            infoDataModel.infoData.accountNumber = accountNumberEdit.text.toString()
        }

        Log.d(TAG, "~~~~~~~~ ${infoDataModel.infoData.phoneNumber}")
        docRef.update("phoneNumber", infoDataModel.infoData.phoneNumber,
            "accountType", infoDataModel.infoData.accountType,
            "accountNumber", infoDataModel.infoData.accountNumber)
            .addOnSuccessListener {
                Log.d(TAG, "##@@@###### info update1 SUCCESS")
            }

        docRef.collection("FirstSurvey").document(Firebase.auth.currentUser!!.uid)
            .update("EngSurvey", infoDataModel.infoData.EngSurvey)
            .addOnSuccessListener {
                Log.d(TAG, "##@@@###### info update2 SUCCESS")
            }
    }
}