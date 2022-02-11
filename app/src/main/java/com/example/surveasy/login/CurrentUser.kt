package com.example.surveasy.login

import android.os.Parcelable
import com.example.surveasy.list.UserSurveyItem
import kotlinx.android.parcel.Parcelize

@Parcelize
class CurrentUser(
    val uid: String? = null,
    val email: String? = null,
    val name: String? = null,
    val fcmToken: String? = null,
    val firstSurvey: Boolean? = null,
    val UserSurveyList: ArrayList<UserSurveyItem>? = null
                  ) : Parcelable {

}