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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Register2Fragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var accountType : String
    private lateinit var inflowPath : String

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
            auth = FirebaseAuth.getInstance()
            register2(view)
            (activity as RegisterActivity).goToRegisterFin()
        }

        return view
    }


    // Register2
    private fun register2(view: View) {
        val accountNumber: Long = view.findViewById<EditText>(R.id.RegisterFragment2_AccountNumberInput).text.toString().toLong()
        val accountOwner: String = view.findViewById<EditText>(R.id.RegisterFragment2_AccountOwnerInput).text.toString()

        if(accountNumber == null) {
            Toast.makeText(context, "계좌번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if(accountOwner == "") {
            Toast.makeText(context, "계좌주를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else {
            val db = Firebase.firestore
            auth = FirebaseAuth.getInstance()
            db.collection("AndroidUser").document(auth.currentUser!!.uid)
                .update("accountType", accountType, "accountNumber", accountNumber,
                    "accountOwner", accountOwner, "inflowPath", inflowPath)
                .addOnSuccessListener { Log.d(TAG, "@@@@@ First Survey field updated!") }
        }

    }


    private fun setAccountTypeSpinner(view: View) {
        val accountTypeList = resources.getStringArray(R.array.accountType)
        val accountTypeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, accountTypeList)
        val accountTypeSpinner : Spinner = view.findViewById(R.id.RegisterFragment2_AccountTypeSpinner)
        accountTypeSpinner.adapter = accountTypeAdapter
        accountTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                accountType = accountTypeList[position]
                Toast.makeText(context, accountTypeList[position], Toast.LENGTH_SHORT).show()
                Log.d(TAG, "@@@@@@@ account type : $accountType")
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
                inflowPath = inflowPathList[position]
                Toast.makeText(context, inflowPathList[position], Toast.LENGTH_SHORT).show()
                Log.d(TAG, "@@@@@@@ inflow : $inflowPath")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }




}