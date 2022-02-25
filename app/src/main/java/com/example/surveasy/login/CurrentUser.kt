package com.example.surveasy.login

import android.os.Parcelable
import com.example.surveasy.list.UserSurveyItem
import kotlinx.android.parcel.Parcelize

// login 한 user 의 정보 list
@Parcelize
class CurrentUser(val uid: String? = null,
                  val fcmToken: String? = null,
                  val name: String? = null,
                  val email: String? = null,
                  val phoneNumber: String? = null,
                  val gender: String? = null,
                  val birthDate: String? = null,
                  val accountType: String? = null,
                  val accountNumber: String? = null,
                  val accountOwner: String? = null,
                  val inflowPath : String? = null,
                  val didFirstSurvey: Boolean? = null,
                  val autoLogin: Boolean? = null,
                  val rewardCurrent : Int? = null,
                  val rewardTotal : Int? = null,
                  val UserSurveyList: ArrayList<UserSurveyItem>? = null ) : Parcelable {

}