package com.example.surveasy.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.example.surveasy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
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
        val agree1 : CheckBox = view.findViewById(R.id.registerAgree1_agree2)
        val agree2 : CheckBox = view.findViewById(R.id.registerAgree1_agree3)
        val agree3 : CheckBox = view.findViewById(R.id.registerAgree1_agree4)


        if(agree1.isChecked && agree2.isChecked){
            //화면 전환
        }else{
            Toast.makeText(context,"필수 항목에 동의해주세요",Toast.LENGTH_LONG).show()
        }
        if(agree3.isChecked){
            db.collection("AndroidUser").document()
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
        }



        return view
    }


}