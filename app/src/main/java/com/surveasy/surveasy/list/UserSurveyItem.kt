package com.surveasy.surveasy.list

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//user 가 참여한 surveyList
@Parcelize
class UserSurveyItem(
    val id : Int,
    val idChecked : Int,
    val title : String?,
    val reward : Int?,
    val responseDate : String?,
    val progress : Int?,
    val isSent : Boolean?,
    val filePath : String?
    ):Parcelable{
}