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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

import com.example.surveasy.R
import com.example.surveasy.login.*
import com.example.surveasy.my.MyViewNoticeListActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
            val intent = Intent(context, Register1Activity::class.java)
            startActivity(intent)
        }

        val home_GreetingText : TextView = view.findViewById(R.id.Home_GreetingText)
        if(userModel.currentUser.uid != null) {
            home_GreetingText.text = "안녕하세요, ${userModel.currentUser.name}님!"
        }

        val user : Button = view.findViewById(R.id.User)
        user.setOnClickListener {
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
            FirebaseMessaging.getInstance().subscribeToTopic("weather")

        }


        val FCMSubscribeBtn : Button = view.findViewById(R.id.FCMSubscribeBtn)
        FCMSubscribeBtn.setOnClickListener {
            Log.d(TAG, "Subscribing to weather topic")
            Firebase.messaging.subscribeToTopic("weather")
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
}