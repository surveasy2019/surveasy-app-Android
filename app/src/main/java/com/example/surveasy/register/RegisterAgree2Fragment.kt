package com.example.surveasy.register

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.surveasy.R
import org.w3c.dom.Text

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



        registerAgree2.setOnClickListener {
            (activity as RegisterActivity).goToRegister1()
        }





        return view
    }
}