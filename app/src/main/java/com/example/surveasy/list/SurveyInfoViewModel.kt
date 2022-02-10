package com.example.surveasy.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SurveyInfoViewModel:ViewModel() {

    val surveyInfo = ArrayList<SurveyItems>()

    fun setData(surveyItems: SurveyItems){
        surveyInfo.add(surveyItems)


    }


}