package com.example.surveasy.list

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserSurveyList(
    val userSurveyList: ArrayList<UserSurveyItem> = ArrayList<UserSurveyItem>())
    :Parcelable {

}