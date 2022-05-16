package com.surveasy.surveasy.register

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.surveasy.surveasy.R

class RegisterAgree2Fragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_register_agree2,container,false)
        val registerAgree2 : Button = view.findViewById(R.id.RegisterAgree2_Btn)
        val textColor : TextView = view.findViewById(R.id.changeColor_text)

        val sen = "불성실한 응답 적발 시, 패널 활동이 강제중지 되며 응답비는 지급되지 않습니다."

        val spannableString = SpannableString(sen)
        spannableString.setSpan(ForegroundColorSpan(Color.RED),21,26,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textColor.text = spannableString


        registerAgree2.setOnClickListener {
            (activity as RegisterActivity).goToRegister1()
        }





        return view
    }
}