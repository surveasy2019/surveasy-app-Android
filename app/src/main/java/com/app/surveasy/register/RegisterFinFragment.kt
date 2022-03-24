package com.app.surveasy.register

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.app.surveasy.R
import com.app.surveasy.login.LoginActivity


class RegisterFinFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_registerfin,container,false)
        (activity as RegisterActivity).toolbarHide()

        val registerFin_Btn : Button = view!!.findViewById(R.id.RegisterFinFragment_Btn)
        registerFin_Btn.setOnClickListener {
            val intent : Intent = Intent(context, LoginActivity::class.java)
            (activity as RegisterActivity).fin()
            startActivity(intent)

        }

        return view
    }


}