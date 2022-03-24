package com.test.surveasy.list

import androidx.lifecycle.ViewModel

//user 가 참여한 survey 중 정산 대기
class WaitUserSurveyListViewModel : ViewModel() {

    val waitSurvey = ArrayList<UserSurveyItem>()
}