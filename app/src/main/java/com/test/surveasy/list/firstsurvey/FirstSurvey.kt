package com.test.surveasy.list.firstsurvey

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class FirstSurvey(
    val job: String? = null,
    val major: String? = null,
    val university: String? = null,
    val EngSurvey: Boolean? = null,
    val military: String? = null,
    var district: String? = null,
    var city: String? = null,
    var married: String? = null,
    var pet: String? = null,
    var family: String? = null,
    var housingType: String? = null

) : Parcelable {
}