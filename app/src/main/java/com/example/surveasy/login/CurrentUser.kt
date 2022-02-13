package com.example.surveasy.login

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CurrentUser(val uid: String? = null,
                  val fcmToken: String? = null,
                  val name: String? = null,
                  val email: String? = null,
                  val phoneNumber: String? = null,
                  val gender: String? = null,
                  val birthDate: String? = null,
                  val accountType: String? = null,
                  val accountNumber: Int? = null,
                  val accountOwner: String? = null,
                  val inflowPath : String? = null,
                  val didFirstSurvey: Boolean? = null,
                  ) : Parcelable {

}