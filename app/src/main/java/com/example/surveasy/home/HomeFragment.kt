package com.example.surveasy.home

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

import com.example.surveasy.R
import com.example.surveasy.list.SurveyInfoViewModel
import com.example.surveasy.list.UserSurveyItem
import com.example.surveasy.login.*
import com.example.surveasy.register.RegisterActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging


class HomeFragment : Fragment() {

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val userModel by activityViewModels<CurrentUserViewModel>()

        val register: Button = view.findViewById(R.id.HomeToRegister)
        val login: Button = view.findViewById(R.id.HomeToLogin)
        val IdText: TextView = view.findViewById(R.id.Home_GreetingText)


        login.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }


        register.setOnClickListener {
            val intent = Intent(context, RegisterActivity::class.java)
            startActivity(intent)
        }

        if(userModel.currentUser.uid != null) {
            val home_GreetingText : TextView = view.findViewById(R.id.Home_GreetingText)
            home_GreetingText.text = "안녕하세요, ${userModel.currentUser.name}님!"
            Log.d(TAG, "*********** ${userModel.currentUser.name}")
        }


        val user : Button = view.findViewById(R.id.User)
        user.setOnClickListener {
            userList()
            user.text = userModel.currentUser.name
            Log.d(TAG, "*********** ${userModel.currentUser.name}")
        }


        val FCMTokenBtn : Button = view.findViewById(R.id.FCMTokenBtn)
        FCMTokenBtn.setOnClickListener {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new FCM registration token
                val token = task.result

                Log.d(TAG, "##### FCM TOKEN:: $token")

                val db = Firebase.firestore
                val fcmToken = hashMapOf(
                    "fcmToken" to token
                )
                db.collection("fcmToken").document(token).set(fcmToken)
            })
            //FirebaseMessaging.getInstance().subscribeToTopic("ad")

        }


        val FCMSubscribeBtn : Button = view.findViewById(R.id.FCMSubscribeBtn)
        FCMSubscribeBtn.setOnClickListener {
            Log.d(TAG, "Subscribing to weather topic")
            Firebase.messaging.subscribeToTopic("ad")
                .addOnCompleteListener { task ->
                    var msg = "This is SUCCESS message!"
                    if(!task.isSuccessful) {
                        msg = "This is FAIL message!"
                    }
                    Log.d(TAG, msg)
                }
        }


        return view


    }

    val userList  = arrayListOf<UserSurveyItem>()

    private fun userList(){


        val model by activityViewModels<SurveyInfoViewModel>()

//        val u =  userList.addAll(UserSurveyItem(
//            500,
//            model.surveyInfo.get(0).title,
//            model.surveyInfo.get(0).date,
//            false
//        ))



        db.collection("AndroidUser").document("gOyfH6eGm7cL24zZn1346iWMu6D3")
            .collection("UserSurveyList").document("설문 ID")
            .set(userList)
    }
}