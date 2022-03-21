package com.app.surveasy.list

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//user 가 참여한 surveyList
@Parcelize
class UserSurveyList(
    val userSurveyList: ArrayList<UserSurveyItem> = ArrayList<UserSurveyItem>())
    :Parcelable {

}