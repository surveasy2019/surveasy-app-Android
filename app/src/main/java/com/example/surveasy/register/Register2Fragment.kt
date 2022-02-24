package com.example.surveasy.register

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import com.example.surveasy.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging


class Register2Fragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    val registerModel by activityViewModels<RegisterInfo1ViewModel>()
    private var accountType : String? = null
    private var inflowPath : String? = null

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

        }

        return view
    }


    // Register2
    private fun register2(view: View) {
        val accountNumber: String = view.findViewById<EditText>(R.id.RegisterFragment2_AccountNumberInput).text.toString()
        val accountOwner: String = view.findViewById<EditText>(R.id.RegisterFragment2_AccountOwnerInput).text.toString()

        if(accountNumber == "") {
            Toast.makeText(context, "계좌번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }else if(accountNumber.contains("-")){
            Toast.makeText(context, "계좌번호란에는 숫자만 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if(accountOwner == "") {
            Toast.makeText(context, "계좌주를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else {
            val db = Firebase.firestore

            auth.createUserWithEmailAndPassword(registerModel.registerInfo1.email!!, registerModel.registerInfo1.password!!)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUID = auth.currentUser!!.uid

                        FirebaseMessaging.getInstance().token.addOnCompleteListener(
                            OnCompleteListener { task ->
                                if (!task.isSuccessful) {
                                    Log.w(
                                        TAG,
                                        "Fetching FCM registration token failed",
                                        task.exception
                                    )
                                    return@OnCompleteListener
                                }
                                val token = task.result

                                val user = hashMapOf(
                                    "uid" to firebaseUID,
                                    "fcmToken" to token,
                                    "name" to registerModel.registerInfo1.name,
                                    "email" to registerModel.registerInfo1.email,
                                    "phoneNumber" to registerModel.registerInfo1.phoneNumber,
                                    "gender" to registerModel.registerInfo1.gender,
                                    "birthDate" to registerModel.registerInfo1.birthDate,
                                    "accountType" to accountType,
                                    "accountNumber" to accountNumber,
                                    "accountOwner" to accountOwner,
                                    "inflowPath" to inflowPath,
                                    "didFirstSurvey" to false,
                                    "pushOn" to false,
                                    "reward_current" to 0,
                                    "reward_total" to 0
                                )
                                db.collection("AndroidUser").document(firebaseUID)
                                    .set(user).addOnSuccessListener { documentReference ->
                                        Log.d(TAG, "##### 회원가입 2 set 성공")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(TAG, "##### 회원가입 2 set 실패", e)
                                    }

                                val firstSurvey = hashMapOf(
                                    "EngSurvey" to false
                                )
                                db.collection("AndroidUser").document(firebaseUID)
                                    .collection("FirstSurvey").document(firebaseUID)
                                    .set(firstSurvey).addOnSuccessListener {
                                        Log.d(TAG, "##### 회원가입 2 set ENG SURVEY 성공")
                                    }
                            })
                        (activity as RegisterActivity).goToRegisterFin()

                    } else {
                        Toast.makeText(
                            context,
                            "#####Auth Error: " + task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

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