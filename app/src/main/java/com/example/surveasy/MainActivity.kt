package com.example.surveasy

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import com.example.surveasy.databinding.ActivityMainBinding
import com.example.surveasy.home.HomeFragment
import com.example.surveasy.list.SurveyInfoViewModel
import com.example.surveasy.list.SurveyItems
import com.example.surveasy.list.SurveyListFragment
import com.example.surveasy.login.CurrentUser
import com.example.surveasy.login.CurrentUserViewModel
import com.example.surveasy.my.MyViewFragment
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    val db = Firebase.firestore
    val surveyList = arrayListOf<SurveyItems>()
    val model by viewModels<SurveyInfoViewModel>()
    val userModel by viewModels<CurrentUserViewModel>()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        db.collection("AppTest1").get()
            .addOnSuccessListener { result->

                for(document in result){
                    val item : SurveyItems = SurveyItems(document["name"] as String, document["recommend"] as String)
                    surveyList.add(item)

                    Log.d(TAG,"${document["name"]} and ${document["recommend"]}")


                }
                model.surveyInfo.addAll(surveyList)


            }
            .addOnFailureListener{exception->
                Log.d(ContentValues.TAG,"fail $exception")
            }

        val transaction = supportFragmentManager.beginTransaction()
        setContentView(binding.root)
        transaction.add(R.id.MainView, HomeFragment()).commit()


        // Current User
        val user = Firebase.auth.currentUser
        user?.let {
            val uid = user.uid
            val email = user.email
        }

        // Current User from LoginActivity
        val intent: Intent = intent
        val currentUser = intent.getParcelableExtra<CurrentUser>("currentUser")
        if(currentUser != null ) {
            userModel.currentUser = currentUser!!
        }

        binding.NavHome.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.MainView, HomeFragment())
                .commit()
        }
        binding.NavList.setOnClickListener {

            supportFragmentManager.beginTransaction()
                .replace(R.id.MainView, SurveyListFragment())
                .commit()

        }
        binding.NavMy.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.MainView, MyViewFragment())
                .commit()
        }


//        val keyHash = Utility.getKeyHash(this)
//        Log.d("Hash",keyHash)



    }

}