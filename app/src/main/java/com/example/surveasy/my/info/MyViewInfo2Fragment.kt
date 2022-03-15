package com.example.surveasy.my.info

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import com.example.surveasy.R
import com.example.surveasy.my.info.InfoDataViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging


class MyViewInfo2Fragment : Fragment() {
    val infoDataModel by activityViewModels<InfoDataViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_myviewinfo2, container, false)
        val engSwitch : Switch = view.findViewById(R.id.MyViewInfo_InfoItem_EngSurveySwitch)

        setAccountTypeSpinner(view, infoDataModel.infoData.accountType!!)
        setEditTextHint(view)

        // EngSurvey
//        val engSurveyRadioGroup = view.findViewById<RadioGroup>(R.id.MyViewInfo_InfoItem_EngSurveyRadioGroup)
//        engSurveyRadioGroup.setOnCheckedChangeListener { engSurveyRadioGroup, checked ->
//            when(checked) {
//                R.id.MyViewInfo_InfoItem_EngSurvey_O -> infoDataModel.infoData.EngSurvey = true
//                R.id.MyViewInfo_InfoItem_EngSurvey_X -> infoDataModel.infoData.EngSurvey = false
//            }
//        }

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
            else {
                infoDataModel.infoData.EngSurvey = false
                engSwitch.text = "희망하지 않음"
            }
        }

        return view

    }

    private fun setAccountTypeSpinner(view: View, previousType: String) {
        val accountTypeList = resources.getStringArray(R.array.accountType)
        val accountTypeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, accountTypeList)
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

        phoneNumberEdit.hint = infoDataModel.infoData.phoneNumber
        accountNumberEdit.hint = infoDataModel.infoData.accountNumber


    }

}