package com.example.surveasy.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//기본 Survey list
class SurveyInfoViewModel:ViewModel() {

    val surveyInfo = ArrayList<SurveyItems>()

    fun setData(surveyItems: SurveyItems){
        surveyInfo.add(surveyItems)


    }


}