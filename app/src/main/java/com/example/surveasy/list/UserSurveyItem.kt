package com.example.surveasy.list

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//user 가 참여한 surveyList
@Parcelize
class UserSurveyItem(
    val id : String?,
    val title : String?,
    val reward : Int?,
    val responseDate : String?,
    ):Parcelable{
}