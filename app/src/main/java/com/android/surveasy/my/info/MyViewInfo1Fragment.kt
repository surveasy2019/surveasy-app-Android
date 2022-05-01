package com.android.surveasy.my.info

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.android.surveasy.R


class MyViewInfo1Fragment : Fragment() {
    val infoDataModel by activityViewModels<InfoDataViewModel>()
    val set: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // (activity as MyViewInfoActivity).fetchInfoData()
        val view = inflater.inflate(R.layout.fragment_myviewinfo1, container, false)

        setVariableInfo(view, infoDataModel.infoData)

        return view

    }

    private fun setVariableInfo(view: View, infoData: InfoData) {
        val phoneNumber = view.findViewById<TextView>(R.id.MyViewInfo_InfoItem_PhoneNumber)
        val accountType = view.findViewById<TextView>(R.id.MyViewInfo_InfoItem_AccountType)
        val accountNumber = view.findViewById<TextView>(R.id.MyViewInfo_InfoItem_AccountNumber)
        val EngSurvey = view.findViewById<TextView>(R.id.MyViewInfo_InfoItem_EngSurvey)

        phoneNumber.text = infoData.phoneNumber
        accountType.text = infoData.accountType
        accountNumber.text = infoData.accountNumber
        if(infoData.EngSurvey == true) {
            EngSurvey.text = "희망함"
            Log.d(ContentValues.TAG, "Frag1 TRUE %%%%%%%%%%%%%%%%")
        }
        else if(infoData.EngSurvey == false) {
            EngSurvey.text = "희망하지 않음"
            Log.d(ContentValues.TAG, "Frag1 FALSE %%%%%%%%%%%%%%%%")
        }
    }

}