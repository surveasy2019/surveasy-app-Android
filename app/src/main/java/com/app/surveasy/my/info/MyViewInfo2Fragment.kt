package com.app.surveasy.my.info

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import com.app.surveasy.R


class MyViewInfo2Fragment : Fragment() {
    val infoDataModel by activityViewModels<InfoDataViewModel>()

    override fun onStart() {
        super.onStart()
        val engSwitch : Switch = requireView().findViewById(R.id.MyViewInfo_InfoItem_EngSurveySwitch)
        if (infoDataModel.infoData.EngSurvey == true) {
            engSwitch.isChecked = true
            engSwitch.text = "희망함"
            Log.d(TAG, "TRUE %%%%%%%%%%%%%%%%")
        }
        else if(infoDataModel.infoData.EngSurvey == false) {
            engSwitch.isChecked = false
            engSwitch.text = "희망하지 않음"
            Log.d(TAG, "FALSE %%%%%%%%%%%%%%%%")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_myviewinfo2, container, false)
        val engSwitch : Switch = view.findViewById(R.id.MyViewInfo_InfoItem_EngSurveySwitch)

        setAccountTypeSpinner(view, infoDataModel.infoData.accountType!!)
        setEditTextHint(view)


        if (infoDataModel.infoData.EngSurvey == true) {
            engSwitch.isChecked = true
            engSwitch.text = "희망함"
        }
        else if(infoDataModel.infoData.EngSurvey == false) {
            engSwitch.isChecked = false
            engSwitch.text = "희망하지 않음"
        }

        engSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                infoDataModel.infoData.EngSurvey = true
                engSwitch.text = "희망함"
            }
            else if(!isChecked) {
                infoDataModel.infoData.EngSurvey = false
                engSwitch.text = "희망하지 않음"
            }
        }

        return view

    }

    private fun spinnerDefault(list : MutableList<String>, i: Int, defaultType: String): MutableList<String> {
        var setDefaultList = list
        setDefaultList.removeAt(i)
        setDefaultList.add(0, "$defaultType")

        return setDefaultList
    }

    private fun setAccountTypeSpinner(view: View, currentType: String) {
        val accountTypeList = resources.getStringArray(R.array.accountType).toMutableList()
        var setDefaultList : MutableList<String> = mutableListOf()
        when(currentType) {
            "국민" -> { setDefaultList = accountTypeList }
            "하나" -> { setDefaultList = spinnerDefault(accountTypeList, 1, "하나") }
            "우리" -> { setDefaultList = spinnerDefault(accountTypeList, 2, "우리") }
            "신한" -> { setDefaultList = spinnerDefault(accountTypeList, 3, "신한") }
            "농협" -> { setDefaultList = spinnerDefault(accountTypeList, 4, "농협") }
            "IBK 기업" -> { setDefaultList = spinnerDefault(accountTypeList, 5, "IBK 기업") }
            "새마을금고" -> { setDefaultList = spinnerDefault(accountTypeList, 6, "새마을금고") }
            "카카오뱅크" -> { setDefaultList = spinnerDefault(accountTypeList, 7, "카카오뱅크") }
        }


        val accountTypeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, setDefaultList)
        val accountTypeSpinner : Spinner = view.findViewById(R.id.MyViewInfo_InfoItem_AccountTypeSpinner)
        accountTypeSpinner.adapter = accountTypeAdapter
        accountTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                infoDataModel.infoData.accountType = accountTypeList[position]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun setEditTextHint(view: View) {
        val phoneNumberEdit = view.findViewById<EditText>(R.id.MyViewInfo_InfoItem_PhoneNumberEdit)
        val accountNumberEdit = view.findViewById<EditText>(R.id.MyViewInfo_InfoItem_AccountNumberEdit)

        val phoneNum = infoDataModel.infoData.phoneNumber.toString()
        phoneNumberEdit.setText(phoneNum)

        val accountNum = infoDataModel.infoData.accountNumber.toString()
        accountNumberEdit.setText(accountNum)

//        phoneNumberEdit.hint = infoDataModel.infoData.phoneNumber
//        accountNumberEdit.hint = infoDataModel.infoData.accountNumber


//        if(phoneNumberEdit.isFocused == true) phoneNumberEdit.setHintTextColor(Color.parseColor("#c9c9c9"))
//        else phoneNumberEdit.setHintTextColor(Color.parseColor("#000000"))
//
//        if(accountNumberEdit.isFocused == true) accountNumberEdit.setHintTextColor(Color.parseColor("#c9c9c9"))
//        else accountNumberEdit.setHintTextColor(Color.parseColor("#000000"))


    }

}