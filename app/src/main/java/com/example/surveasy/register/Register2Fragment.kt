package com.example.surveasy.register

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.surveasy.R


class Register2Fragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_register2,container,false)

        // Set Spinners
        setAccountTypeSpinner(view)
        setInflowPathSpinner(view)


        val registerFragment2_Btn : Button = view!!.findViewById(R.id.RegisterFragment2_Btn)
        registerFragment2_Btn.setOnClickListener {
            (activity as RegisterActivity).goToRegisterFin()
        }

        return view
    }

    private fun setAccountTypeSpinner(view: View) {
        val accountTypeList = resources.getStringArray(R.array.accountType)
        val accountTypeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, accountTypeList)
        val accountTypeSpinner : Spinner = view.findViewById(R.id.RegisterFragment2_AccountTypeSpinner)
        accountTypeSpinner.adapter = accountTypeAdapter
        accountTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position != 0) {
                    Toast.makeText(context, accountTypeList[position], Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "@@@@@@@ adapter")
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun setInflowPathSpinner(view: View) {
        val inflowPathList = resources.getStringArray(R.array.inflowPath)
        val inflowPathAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, inflowPathList)
        val inflowPathSpinner : Spinner = view.findViewById(R.id.RegisterFragment2_InflowPathSpinner)
        inflowPathSpinner.adapter = inflowPathAdapter
        inflowPathSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(context, inflowPathList[position], Toast.LENGTH_SHORT).show()
                Log.d(TAG, "@@@@@@@ adapter")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }


}