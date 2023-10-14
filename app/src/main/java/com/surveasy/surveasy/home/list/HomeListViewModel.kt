package com.surveasy.surveasy.home.list

import androidx.lifecycle.ViewModel
import com.surveasy.surveasy.list.SurveyItems

//홈 fragment에 들어갈 리스트
class HomeListViewModel :ViewModel() {
    val HomeList = ArrayList<SurveyItems>()
}