package com.example.surveasy.list


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.surveasy.R
import com.example.surveasy.login.UserItems
import com.example.surveasy.login.UserItemsAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SurveyListFragment() : Fragment() {

    val db = Firebase.firestore
    val surveyList = arrayListOf<SurveyItems>()
    val adapter = SurveyItemsAdapter(surveyList)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_surveylist,container,false)




        val model by activityViewModels<SurveyInfoViewModel>()
        val adapter = SurveyItemsAdapter(model.surveyInfo)


        val container : RecyclerView? = view.findViewById(R.id.recyclerContainer)

        container?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        container?.adapter = SurveyItemsAdapter(model.surveyInfo)


        return view
    }


}