package com.example.surveasy.list


import android.content.ContentValues
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
import com.example.surveasy.MainActivity
import com.example.surveasy.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*


class SurveyListFragment() : Fragment() {

    val db = Firebase.firestore
    val surveyList = arrayListOf<SurveyItems>()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_surveylist,container,false)

        CoroutineScope(Dispatchers.Main).launch {

        }


        val model by activityViewModels<SurveyInfoViewModel>()
        val container : RecyclerView? = view.findViewById(R.id.recyclerContainer)

        if(model.surveyInfo.size==0){
            db.collection("AppTest1").get()
                .addOnSuccessListener { result->

                    for(document in result){
                        val item : SurveyItems = SurveyItems(document["name"] as String, document["recommend"] as String, document["url"] as String)
                        surveyList.add(item)

                        Log.d(TAG,"####firestore" )
                    }
                    val adapter = SurveyItemsAdapter(surveyList)
                    container?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                    container?.adapter = SurveyItemsAdapter(surveyList)

                }
                .addOnFailureListener{exception->
                    Log.d(ContentValues.TAG,"fail $exception")
                }
        }else{
            Log.d(TAG,"####viewModel" )
            val adapter = SurveyItemsAdapter(model.surveyInfo)
            container?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            container?.adapter = SurveyItemsAdapter(model.surveyInfo)
        }




        return view
    }



}