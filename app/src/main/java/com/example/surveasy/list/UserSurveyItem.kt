package com.example.surveasy.list

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserSurveyItem(val reward : Int?,
                     val id : String?,
                     val responseDate : String?,
                     val isSent : Boolean?):Parcelable{
}