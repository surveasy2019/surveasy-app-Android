package com.example.surveasy.list


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.surveasy.MainActivity
import com.example.surveasy.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SurveyListFirstSurvey2Fragment() : Fragment() {

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_surveylistfirstsurvey1,container,false)

        val surveyListFirstSurvey2_Btn : Button = view.findViewById(R.id.SurveyListFirstSurvey2_Btn)

        surveyListFirstSurvey2_Btn.setOnClickListener{
            val intent : Intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }

        return view
    }


}