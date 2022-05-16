package com.surveasy.surveasy.list.firstsurvey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.surveasy.surveasy.MainActivity
import com.surveasy.surveasy.R
import com.surveasy.surveasy.login.CurrentUserViewModel

class FirstSurveyListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userModel by activityViewModels<CurrentUserViewModel>()
        val view = inflater.inflate(R.layout.fragment_firstsurveylist, container, false)
        val item : LinearLayout = view.findViewById(R.id.firstSurveyContainer)
        val itemName : TextView = view.findViewById(R.id.FirstSurveyListItemName)

        item.setOnClickListener {
            (activity as MainActivity).clickItem()
        }

        itemName.text = "${userModel.currentUser.name}님에 대해 알려주세요!"


        return view
    }
}