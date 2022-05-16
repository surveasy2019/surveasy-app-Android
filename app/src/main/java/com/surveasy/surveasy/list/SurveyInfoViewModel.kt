package com.surveasy.surveasy.list

import androidx.lifecycle.ViewModel

//기본 Survey list
class SurveyInfoViewModel:ViewModel() {

    val surveyInfo = ArrayList<SurveyItems>()

    fun setData(surveyItems: SurveyItems){
        surveyInfo.add(surveyItems)
    }
    //마감 임박순으로 정렬
    fun sortSurvey() : ArrayList<SurveyItems>{
        val surveylist = arrayListOf<SurveyItems>()
        surveyInfo.sortWith(compareBy<SurveyItems> { it.dueDate }.thenBy { it.dueTimeTime })
        surveylist.addAll(surveyInfo)

        return surveylist
    }

    //최신 등록순 정렬
    fun sortSurveyRecent() : ArrayList<SurveyItems>{
        val surveylist = arrayListOf<SurveyItems>()
        surveyInfo.sortWith(compareByDescending<SurveyItems> { it.uploadDate })
        surveylist.addAll(surveyInfo)

        return surveylist
    }



}