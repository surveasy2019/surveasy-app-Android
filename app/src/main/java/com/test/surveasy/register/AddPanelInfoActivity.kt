package com.test.surveasy.register

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.test.surveasy.R
import com.test.surveasy.databinding.ActivityAddPanelInfoBinding
import com.test.surveasy.login.CurrentUser
import java.util.*

class AddPanelInfoActivity : AppCompatActivity() {

    var gender : String? = null
    var birthDate: String? = null
    var inflowPath: String? = null
    var accountType : String? = null
    private lateinit var binding : ActivityAddPanelInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityAddPanelInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setInflowPathSpinner()
        setAccountTypeSpinner()

        val currentUser = intent.getParcelableExtra<CurrentUser>("currentUser_login")!!

        // gender
        binding.addInfoRadioGroup.setOnCheckedChangeListener { genderRadioGroup, checkedId ->
            when (checkedId) {
                R.id.addInfo_RadioMale -> gender = "남"
                R.id.addInfo_RadioFemale -> gender = "여"

            }
            Log.d(ContentValues.TAG, "~~~~~~~~gender: $gender")
        }

        // BirthDate
        birthDate = initYearPicker() + "-" + initMonthPicker() + "-" + initDayPicker()

        binding.addInfoBtn.setOnClickListener {
            register1()
        }

    }

    private fun register1() {
        val currentUser = intent.getParcelableExtra<CurrentUser>("currentUser_login")!!
        birthDate = initYearPicker() + "-" + initMonthPicker() + "-" + initDayPicker()
        val accountNumber: String = findViewById<EditText>(R.id.addInfo_AccountNumberInput).text.toString()
        val accountOwner: String = findViewById<EditText>(R.id.addInfo_AccountOwnerInput).text.toString()

        Log.d(ContentValues.TAG, "@@@@@@@@------- birthdate : $birthDate")

        if(gender == null) {
            Toast.makeText(this, "성별을 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if (birthDate == null) {
            Toast.makeText(this, "생년월일을 선택해주세요.", Toast.LENGTH_SHORT).show()

        }
        else if (inflowPath == "유입경로를 선택하세요") {
            Toast.makeText(this, "유입경로를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }else if(accountType == "은행을 선택하세요") {
            Toast.makeText(this, "은행을 선택해주세요.", Toast.LENGTH_SHORT).show()
        }else if(accountNumber == "") {
            Toast.makeText(this, "계좌번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }else if(accountNumber.contains("-")){
            Toast.makeText(this, "계좌번호란에는 숫자만 입력해주세요.", Toast.LENGTH_SHORT).show()
        }else if(accountOwner == "") {
            Toast.makeText(this, "계좌주를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }else if(accountNumber.contains(" ") || accountOwner.contains(" ")){
            Toast.makeText(this,"입력란의 공백을 지워주세요.",Toast.LENGTH_LONG).show()
        }else {
            val db = Firebase.firestore
            currentUser.birthDate = birthDate
            currentUser.gender = gender
            currentUser.inflowPath = inflowPath
            currentUser.accountType = accountType
            currentUser.accountNumber = accountNumber
            currentUser.accountOwner = accountOwner
            //intent 이동

            val firebaseUID = currentUser.uid.toString()
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
                    val token = task.result

                    val user = hashMapOf(
                        "uid" to firebaseUID,
                        "fcmToken" to token,
                        "name" to currentUser.name,
                        "email" to currentUser.email,
                        "phoneNumber" to currentUser.phoneNumber,
                        "gender" to currentUser.gender,
                        "birthDate" to currentUser.birthDate,
                        "accountType" to currentUser.accountType,
                        "accountNumber" to currentUser.accountNumber,
                        "accountOwner" to currentUser.accountOwner,
                        "inflowPath" to currentUser.inflowPath,
                        "didFirstSurvey" to false,
                        "pushOn" to false,
                        "reward_current" to 0,
                        "reward_total" to 0,
                        "marketingAgree" to currentUser.marketingAgree,
                        "autoLogin" to false
                    )
                    db.collection("AndroidUser").document(firebaseUID)
                        .set(user).addOnSuccessListener { documentReference ->
                            Log.d(ContentValues.TAG, "##### 회원가입 2 set 성공")
                        }
                        .addOnFailureListener { e ->
                            Log.w(ContentValues.TAG, "##### 회원가입 2 set 실패", e)
                        }

                    val firstSurvey = hashMapOf(
                        "EngSurvey" to false
                    )
                    db.collection("AndroidUser").document(firebaseUID)
                        .collection("FirstSurvey").document(firebaseUID)
                        .set(firstSurvey).addOnSuccessListener {
                            Log.d(ContentValues.TAG, "##### 회원가입 2 set ENG SURVEY 성공")
                        }
                })
            //(activity as RegisterActivity).goToRegisterFin()

//            auth.createUserWithEmailAndPassword(registerModel.registerInfo1.email!!, registerModel.registerInfo1.password!!)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//
//
//
//
//                    } else {
//                        Toast.makeText(
//                            this,
//                            "회원가입 과정에서 문제가 발생했습니다. 다시 시도해주세요",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//
//                }

        }

    }

    private fun initYearPicker(): String {
        val today = Calendar.getInstance()
        val currentYear = today.get(Calendar.YEAR)
        val numberPicker = findViewById<NumberPicker>(R.id.addInfo_Year)
//        numberPicker.minValue = currentYear - 35
        numberPicker.minValue = 1990
        numberPicker.maxValue = currentYear
        numberPicker.wrapSelectorWheel = false

        var year : Int = numberPicker.minValue
        year = numberPicker.value
        return year.toString()
    }
    private fun initMonthPicker(): String {
        val numberPicker = findViewById<NumberPicker>(R.id.addInfo_Month)
        numberPicker.minValue = 1
        numberPicker.maxValue = 12
        numberPicker.wrapSelectorWheel = false

        var month : Int = numberPicker.minValue
        month = numberPicker.value
        var monthStr: String = month.toString()
        if (month < 10) monthStr = "0" + monthStr
        return monthStr
    }

    private fun initDayPicker(): String {
        val numberPicker = findViewById<NumberPicker>(R.id.addInfo_Date)
        numberPicker.minValue = 1
        numberPicker.maxValue = 31
        numberPicker.wrapSelectorWheel = false

        var day : Int = numberPicker.minValue
        day = numberPicker.value
        var dayStr: String = day.toString()
        if (day < 10) dayStr = "0" + dayStr
        return dayStr
    }
    private fun setInflowPathSpinner() {
        val inflowPathList = resources.getStringArray(R.array.inflowPath)
        val inflowPathAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, inflowPathList)
        val inflowPathSpinner : Spinner = findViewById(R.id.addInfo_InflowPathSpinner)
        inflowPathSpinner.adapter = inflowPathAdapter
        inflowPathSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                inflowPath = inflowPathList[position]
                Log.d(ContentValues.TAG, "@@@@@@@ inflow : $inflowPath")
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun setAccountTypeSpinner() {
        val accountTypeList = resources.getStringArray(R.array.accountType)
        val accountTypeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, accountTypeList)
        val accountTypeSpinner : Spinner = findViewById(R.id.addInfo_AccountTypeSpinner)
        accountTypeSpinner.adapter = accountTypeAdapter
        accountTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                accountType = accountTypeList[position]

                //Toast.makeText(context, accountTypeList[position], Toast.LENGTH_SHORT).show()

                Log.d(ContentValues.TAG, "@@@@@@@ account type : $accountType")
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }
}