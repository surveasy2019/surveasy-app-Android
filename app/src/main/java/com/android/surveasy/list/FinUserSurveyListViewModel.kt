package com.android.surveasy.list

import androidx.lifecycle.ViewModel

//user 가 참여한 survey 중 정산 완료
class FinUserSurveyListViewModel : ViewModel() {
    val finSurvey = ArrayList<UserSurveyItem>()
}