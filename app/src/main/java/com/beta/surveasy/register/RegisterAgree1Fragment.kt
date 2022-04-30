package com.beta.surveasy.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.beta.surveasy.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterAgree1Fragment : Fragment() {

    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_register_agree1,container,false)
        val agreeAll : CheckBox = view.findViewById(R.id.registerAgree1_agree1)
        val agreeAllText : TextView = view.findViewById(R.id.register_allAgree_text)
        val agree1Text : TextView = view.findViewById(R.id.register_1Agree_text)
        val agree2Text : TextView = view.findViewById(R.id.register_2Agree_text)
        val agree3Text : TextView = view.findViewById(R.id.register_3Agree_text)
        val agree1 : CheckBox = view.findViewById(R.id.registerAgree1_agree2)
        val term1 : TextView = view.findViewById(R.id.register_goTerm1)
        val agree2 : CheckBox = view.findViewById(R.id.registerAgree1_agree3)
        val term2 : TextView = view.findViewById(R.id.register_goTerm2)
        val agree3 : CheckBox = view.findViewById(R.id.registerAgree1_agree4)
        val registerAgree1 : Button = view.findViewById(R.id.RegisterAgree1_Btn)
        val text : TextView = view.findViewById(R.id.SNSAgree_text)
        val registerModel by activityViewModels<RegisterInfo1ViewModel>()



        registerAgree1.setOnClickListener {
            if(agree1.isChecked && agree2.isChecked){
                (activity as RegisterActivity).goAgree2()
            }else{
                Toast.makeText(context,"필수 항목에 동의해주세요",Toast.LENGTH_LONG).show()
            }
            if(agree3.isChecked){
                registerModel.registerInfo1.marketingAgree = true
            }
        }

        term1.setOnClickListener {
            val intent = Intent(context,RegisterTerm1::class.java)
            startActivity(intent)
        }
        term2.setOnClickListener {
            val intent = Intent(context,RegisterTerm2::class.java)
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




        return view
    }


}