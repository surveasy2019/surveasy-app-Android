package com.example.surveasy.list


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.surveasy.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SurveyListFirstSurvey1Fragment() : Fragment() {

    val db = Firebase.firestore
    private lateinit var job : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_surveylistfirstsurvey1,container,false)

        setJobSpinner(view)

        val surveyListFirstSurvey1Btn : Button = view.findViewById(R.id.SurveyListFirstSurvey1_Btn)
        surveyListFirstSurvey1Btn.setOnClickListener {
            (activity as SurveyListFirstSurveyActivity).next()
        }

        return view
    }

    private fun setJobSpinner(view: View) {
        val jobList = resources.getStringArray(R.array.job)
        val jobAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, jobList)
        val jobSpinner : Spinner = view.findViewById(R.id.SurveyListFirstSurvey_JobSpinner)
        jobSpinner.adapter = jobAdapter
        jobSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position != 0) {
                    job = jobList[position]
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }




}