package com.surveasy.surveasy.register

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import com.surveasy.surveasy.R
import com.google.firebase.auth.FirebaseAuth
import java.util.*


class Register1Fragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    val registerModel by activityViewModels<RegisterInfo1ViewModel>()
    var gender : String? = null
    var birthDate: String? = null
    var inflowPath: String? = null
    var cal = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_register1, container, false)

        setInflowPathSpinner(view)


        // gender
        val genderRadioGroup = view.findViewById<RadioGroup>(R.id.RegisterFragment1_RadioGroup)
        genderRadioGroup.setOnCheckedChangeListener { genderRadioGroup, checkedId ->
            when (checkedId) {
                R.id.RegisterFragment1_RadioMale -> gender = "남"
                R.id.RegisterFragment1_RadioFemale -> gender = "여"

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
        if(inflowPath == "기타" || inflowPath == "") inflowPath = view.findViewById<EditText>(R.id.RegisterFragment1_EtcInflowInput).text.toString()
        birthDate = initYearPicker(view) + "-" + initMonthPicker(view) + "-" + initDayPicker(view)
        Log.d(TAG, "@@@@@@@@------- birthdate : $birthDate")

        if(name == "") {
            Toast.makeText(context, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
        }else if(name.length==1){
            Toast.makeText(context, "이름을 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
        }else if(name.contains(" ")){
            Toast.makeText(context, "이름란의 공백을 지워주세요.", Toast.LENGTH_SHORT).show()
        }
        else if (email == "" || !email.contains("@") || !email.contains(".")) {
            Toast.makeText(context, "이메일을 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
        }else if(email.contains(" ")){
            Toast.makeText(context, "아이디란의 공백을 지워주세요.", Toast.LENGTH_SHORT).show()
        }
        else if (password == "") {
            Toast.makeText(context, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }else if(password.length<8){
            Toast.makeText(context, "8자리 이상의 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if (password != passwordCheck) {
            Toast.makeText(context, "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if(phoneNumber.contains("-")||phoneNumber.contains(".") || phoneNumber.contains(" ")){
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
        else if (inflowPath == "유입경로를 선택하세요") {
            Toast.makeText(context, "유입경로를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if(inflowPath == "") {
            Toast.makeText(context, "기타 유입경로를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else {
            val registerInfo1 = RegisterInfo1(null, null, name, email, password, phoneNumber, gender, birthDate, inflowPath, registerModel.registerInfo1.marketingAgree)

            registerModel.registerInfo1 = registerInfo1
            (activity as RegisterActivity).goToRegister2()

        }

    }

    // Birth Pickers
    private fun initYearPicker(view: View): String {
        val today = Calendar.getInstance()
        val currentYear = today.get(Calendar.YEAR)
        val numberPicker = view.findViewById<NumberPicker>(R.id.RegisterFragment1_Year)
//        numberPicker.minValue = currentYear - 35
        numberPicker.minValue = 1950
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

    private fun setInflowPathSpinner(view: View) {
        val inflowPathList = resources.getStringArray(R.array.inflowPath)
        val inflowPathAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, inflowPathList)
        val inflowPathSpinner : Spinner = view.findViewById(R.id.RegisterFragment1_InflowPathSpinner)
        val etcInflowPathContainer : LinearLayout = view.findViewById(R.id.EtcInflowPath_Container)

        inflowPathSpinner.adapter = inflowPathAdapter
        inflowPathSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                inflowPath = inflowPathList[position]
//                Log.d(TAG, "@@@@@@@ inflow : $inflowPath")
                if(inflowPath == "기타") {
                    etcInflowPathContainer.visibility = View.VISIBLE
                }
                else etcInflowPathContainer.visibility = View.GONE
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }
}