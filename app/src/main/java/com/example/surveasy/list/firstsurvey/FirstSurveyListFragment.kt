package com.example.surveasy.list.firstsurvey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.surveasy.MainActivity
import com.example.surveasy.R

class FirstSurveyListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_firstsurveylist, container, false)
        val item : LinearLayout = view.findViewById(R.id.firstSurveyContainer)

        item.setOnClickListener {
            (activity as MainActivity).clickItem()
        }




        return view
    }
}