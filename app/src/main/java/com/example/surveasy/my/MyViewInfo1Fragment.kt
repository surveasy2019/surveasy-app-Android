package com.example.surveasy.my

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.surveasy.R


class MyViewInfo1Fragment : Fragment() {
    val infoDataModel by activityViewModels<InfoDataViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // (activity as MyViewInfoActivity).fetchInfoData()
        val view = inflater.inflate(R.layout.fragment_myviewinfo1, container, false)



        return view

    }



}