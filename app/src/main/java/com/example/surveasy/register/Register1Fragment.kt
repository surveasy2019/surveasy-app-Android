package com.example.surveasy.register

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.surveasy.R
import com.example.surveasy.login.CurrentUserViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*


class Register1Fragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    var gender : String? = null
    var birthDate: String? = null
    var year : String? = null
    var month : String? = null
    var day : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_register1, container, false)


        // gender
        val genderRadioGroup = view.findViewById<RadioGroup>(R.id.RegisterFragment1_RadioGroup)
        genderRadioGroup.setOnCheckedChangeListener { genderRadioGroup, checkedId ->
            when (checkedId) {
                R.id.RegisterFragment1_RadioMale -> gender = "남"
                R.id.RegisterFragment1_RadioFemale -> gender = "여"
                R.id.RegisterFragment1_RadioEtc -> gender = "기타"
            }
            Log.d(TAG, "~~~~~~~~gender: $gender")
        }


        year = initYearPicker(view)
        month = initMonthPicker(view)
        day = initDayPicker(view)


        val registerFragment1Btn: Button = view.findViewById(R.id.RegisterFragment1_Btn)
        registerFragment1Btn.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            register1(view)
        }

        return view
    }


    // Register1
    private fun register1(view: View) {
        val name: String = view.findViewById<EditText>(R.id.RegisterFragment1_NameInput).text.toString()
        val email: String = view.findViewById<EditText>(R.id.RegisterFragment1_EmailInput).text.toString()
        val password = view.findViewById<EditText>(R.id.RegisterFragment1_PwInput).text.toString()
        val passwordCheck = view.findViewById<EditText>(R.id.RegisterFragment1_PwCheckInput).text.toString()
        val phoneNumber: String = view.findViewById<EditText>(R.id.RegisterFragment1_PhoneNumberInput).text.toString()
        year = initYearPicker(view)
        month = initMonthPicker(view)
        day = initDayPicker(view)
        birthDate = year + "-" + month + "-" + day
        Log.d(TAG, "이거!!!!!!!!!!!! $birthDate")

        if(name == "") {
            Toast.makeText(context, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
        }else if(name.length==1 || name.contains(" ")){
            Toast.makeText(context, "이름을 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if (email == "" || !email.contains("@") || !email.contains(".")) {
            Toast.makeText(context, "이메일을 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if (password == "") {
            Toast.makeText(context, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }else if(password.length<8){
            Toast.makeText(context, "8자리 이상의 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if (password != passwordCheck) {
            Toast.makeText(context, "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if(phoneNumber.contains("-")||phoneNumber.contains(".")){
            Toast.makeText(context, "휴대폰번호란에는 숫자만 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if (phoneNumber == "" || phoneNumber.length != 11) {
            Toast.makeText(context, "휴대폰번호를 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if (gender == null) {
            Toast.makeText(context, "성별을 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
        else {
            val db = Firebase.firestore

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUID = auth.currentUser!!.uid

                        FirebaseMessaging.getInstance().token.addOnCompleteListener(
                            OnCompleteListener { task ->
                                if (!task.isSuccessful) {
                                    Log.w(
                                        TAG,
                                        "Fetching FCM registration token failed",
                                        task.exception
                                    )
                                    return@OnCompleteListener
                                }
                                val token = task.result

                                val user = hashMapOf(
                                    "uid" to firebaseUID,
                                    "fcmToken" to token,
                                    "name" to name,
                                    "email" to email,
                                    "phoneNumber" to phoneNumber,
                                    "gender" to gender,
                                    "birthDate" to birthDate,
                                    "accountType" to "",
                                    "accountNumber" to "",
                                    "accountOwner" to "",
                                    "inflowPath" to "",
                                    "didFirstSurvey" to false,
                                    "reward_current" to 0,
                                    "reward_total" to 0
                                )
                                db.collection("AndroidUser").document(firebaseUID)
                                    .set(user).addOnSuccessListener { documentReference ->
                                        Log.d(TAG, "##### 회원가입 1 set 성공")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(TAG, "##### 회원가입 1 set 실패", e)
                                    }
                            })
                        (activity as RegisterActivity).goToRegister2()

                    } else {
                        Toast.makeText(
                            context,
                            "#####Auth Error: " + task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
        }
    }

    // Birth Pickers
    private fun initYearPicker(view: View): String {
        val today = Calendar.getInstance()
        val currentYear = today.get(Calendar.YEAR)
        val numberPicker = view.findViewById<NumberPicker>(R.id.RegisterFragment1_Year)
        numberPicker.minValue = currentYear - 35
        numberPicker.maxValue = currentYear
        numberPicker.wrapSelectorWheel = false

        var year : Int = numberPicker.minValue
        year = numberPicker.value
        return year.toString()
    }

    private fun initMonthPicker(view: View): String {
        val numberPicker = view.findViewById<NumberPicker>(R.id.RegisterFragment1_Month)
        numberPicker.minValue = 1
        numberPicker.maxValue = 12
        numberPicker.wrapSelectorWheel = false

        var month : Int = numberPicker.minValue
        month = numberPicker.value
        var monthStr: String = month.toString()
        if (month < 10) monthStr = "0" + monthStr
        return monthStr
    }

    private fun initDayPicker(view: View): String {
        val numberPicker = view.findViewById<NumberPicker>(R.id.RegisterFragment1_Date)
        numberPicker.minValue = 1
        numberPicker.maxValue = 31
        numberPicker.wrapSelectorWheel = false

        var day : Int = numberPicker.minValue
        day = numberPicker.value
        var dayStr: String = day.toString()
        if (day < 10) dayStr = "0" + dayStr
        return dayStr
    }
}