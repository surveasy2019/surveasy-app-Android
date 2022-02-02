package com.example.surveasy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.surveasy.databinding.ActivityMainBinding
import com.example.surveasy.home.HomeFragment
import com.example.surveasy.list.SurveyListFragment
import com.example.surveasy.my.MyViewFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    val db = Firebase.firestore

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val transaction = supportFragmentManager.beginTransaction()
        setContentView(binding.root)
        transaction.add(R.id.MainView, HomeFragment()).commit()

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

    }
}