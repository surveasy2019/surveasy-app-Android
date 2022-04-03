package com.test.surveasy.login

import android.os.Parcelable
import com.test.surveasy.list.UserSurveyItem
import kotlinx.android.parcel.Parcelize

// login 한 user 의 정보 list
@Parcelize
class CurrentUser(var uid: String? = null,
                  var fcmToken: String? = null,
                  var name: String? = null,
                  var email: String? = null,
                  var phoneNumber: String? = null,
                  var gender: String? = null,
                  var birthDate: String? = null,
                  var accountType: String? = null,
                  var accountNumber: String? = null,
                  var accountOwner: String? = null,
                  var inflowPath : String? = null,
                  var didFirstSurvey: Boolean? = null,
                  var autoLogin: Boolean? = null,
                  var rewardCurrent : Int? = null,
                  var rewardTotal : Int? = null,
                  var marketingAgree: Boolean? = null,
                  var UserSurveyList: ArrayList<UserSurveyItem>? = null ) : Parcelable {

}