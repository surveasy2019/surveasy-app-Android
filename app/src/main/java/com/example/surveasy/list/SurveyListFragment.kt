package com.example.surveasy.list


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.surveasy.R
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

//        val surveyList = arrayListOf(
//            SurveyItems("title",12),
//            SurveyItems("title",13),
//            SurveyItems("title",13),SurveyItems("title",13),
//            SurveyItems("title",13),
//            SurveyItems("title",13)
//
//
//        )



            db.collection("AppTest1").get()
                .addOnSuccessListener { result->
                    surveyList.clear()
                    for(document in result){
                        var item : SurveyItems = SurveyItems(document["name"] as String, document["recommend"] as String)
                        surveyList.add(item)
                        //Log.d(TAG,"${document["name"]} and ${document["recommend"]}")

                        val container : RecyclerView? = view.findViewById(R.id.recyclerContainer)

                        container?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                        container?.adapter = SurveyItemsAdapter(surveyList)
                    }


                }
                .addOnFailureListener{exception->
                    Log.d(TAG,"fail $exception")
                }











        return view
    }


}