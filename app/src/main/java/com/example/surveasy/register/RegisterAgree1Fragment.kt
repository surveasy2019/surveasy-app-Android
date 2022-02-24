package com.example.surveasy.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.example.surveasy.R
import com.google.firebase.auth.FirebaseAuth

class RegisterAgree1Fragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_register_agree1,container,false)


        val registerAgree1Btn : Button = view.findViewById(R.id.RegisterAgree1_Btn)
        registerAgree1Btn.setOnClickListener {
            (activity as RegisterActivity).goToRegister1()
        }


        return view
    }
}