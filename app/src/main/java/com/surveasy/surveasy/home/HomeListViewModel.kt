package com.surveasy.surveasy.home

import androidx.lifecycle.ViewModel
import com.surveasy.surveasy.list.SurveyItems

//홈 fragment에 들어갈 리스트
class HomeListViewModel :ViewModel() {
    val HomeList = ArrayList<SurveyItems>()
}