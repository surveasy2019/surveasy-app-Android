package com.example.surveasy.list.firstsurvey

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.example.surveasy.R
import com.example.surveasy.login.CurrentUser

//first survey 만 있는 리스트
class FirstSurveyListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_first_survey_list,container,false)
        val box = view.findViewById<LinearLayout>(R.id.FirstSurveyListItemContainer)
//        val currentUser = intent.getParcelableExtra<CurrentUser>("currentUser_main")
//
//        box.setOnClickListener {
//            val intent = Intent(this,SurveyListFirstSurveyActivity::class.java)
//            val userList = intent.putExtra("currentUser_main", currentUser)
//            startActivityForResult(intent,101)
//        }




        return view
    }
}
