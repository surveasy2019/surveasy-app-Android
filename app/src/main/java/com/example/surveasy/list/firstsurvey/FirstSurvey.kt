package com.example.surveasy.list.firstsurvey

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class FirstSurvey(
    val job: String? = null,
    val major: String? = null,
    val university: String? = null,
    val EngSurvey: Boolean? = null,
    val military: String? = null,
    val district: String? = null,
    val city: String? = null,
    val married: String? = null,
    val pet: String? = null,
    val family: String? = null,
    val housingType: String? = null

) : Parcelable {
}