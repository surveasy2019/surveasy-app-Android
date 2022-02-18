package com.example.surveasy.my.info

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class InfoData(val name: String?,
               val birthDate: String?,
               val gender: String?,
               val email: String?,
               var phoneNumber: String?,
               var accountType: String?,
               var accountNumber: String?,
               var EngSurvey: Boolean?) : Parcelable {


}