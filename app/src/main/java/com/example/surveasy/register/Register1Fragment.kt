package com.example.surveasy.register

import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import com.example.surveasy.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import java.text.SimpleDateFormat
import java.util.*


class Register1Fragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    val registerModel by activityViewModels<RegisterInfo1ViewModel>()
    var gender : String? = null
    var birthDate: String? = null
    var cal = Calendar.getInstance()

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

        // BirthDate
        birthDate = initYearPicker(view) + "-" + initMonthPicker(view) + "-" + initDayPicker(view)



//        // Create OnDateSetListener
//        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
//            override fun onDateSet(view1: DatePicker?, year: Int, month: Int, day: Int) {
//                cal.set(Calendar.YEAR, year)
//                cal.set(Calendar.MONTH, month)
//                cal.set(Calendar.DAY_OF_MONTH, day)
//                getBirthDate(view)
//            }
//        }
//
//        // Show DatePickerDialog
//        val birthDatePickerBtn = view.findViewById<Button>(R.id.RegisterFragment1_BirthDatePickerBtn)
//        birthDatePickerBtn.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(view: View) {
//                DatePickerDialog(context!!, dateSetListener,
//                    cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
//                    .show()
//            }
//        })


        val registerFragment1Btn: Button = view.findViewById(R.id.RegisterFragment1_Btn)
        registerFragment1Btn.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            register1(view)
        }

        return view
    }


//    private fun getBirthDate(view: View) {
//        val format = "yyyy-MM-dd"
//        val sdf = SimpleDateFormat(format)
//        birthDate = sdf.format(cal.time)
//        val birthDateSelected = view.findViewById<TextView>(R.id.RegisterFragment1_BirthDateSelected)
//        birthDateSelected.text = birthDate
//        Log.d(TAG, "@@@@@@@@------- birthdate : $birthDate")
//
//    }


    // Register1
    private fun register1(view: View) {
        val name: String = view.findViewById<EditText>(R.id.RegisterFragment1_NameInput).text.toString()
        val email: String = view.findViewById<EditText>(R.id.RegisterFragment1_EmailInput).text.toString()
        val password = view.findViewById<EditText>(R.id.RegisterFragment1_PwInput).text.toString()
        val passwordCheck = view.findViewById<EditText>(R.id.RegisterFragment1_PwCheckInput).text.toString()
        val phoneNumber: String = view.findViewById<EditText>(R.id.RegisterFragment1_PhoneNumberInput).text.toString()
        birthDate = initYearPicker(view) + "-" + initMonthPicker(view) + "-" + initDayPicker(view)
        Log.d(TAG, "@@@@@@@@------- birthdate : $birthDate")

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
        else if (birthDate == null) {
            Toast.makeText(context, "생년월일을 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
        else {
            val registerInfo1 = RegisterInfo1(null, null, name, email, password, phoneNumber, gender, birthDate, null)
            registerModel.registerInfo1 = registerInfo1
            (activity as RegisterActivity).goToRegister2()

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