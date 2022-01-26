package com.example.surveasy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment


class SurveyListFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_surveylist,container,false)

        val surveyListContainer = view.findViewById<ConstraintLayout>(R.id.SurveyList_ItemContainer)

        surveyListContainer.setOnClickListener {
            val intent = Intent(context, SurveyListDetailActivity::class.java)
            startActivity(intent)
        }





        return view
    }


}