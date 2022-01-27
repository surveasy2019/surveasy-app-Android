package com.example.surveasy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.surveasy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

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