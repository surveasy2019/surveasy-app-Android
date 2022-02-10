package com.example.surveasy.list

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class SurveyItems(val title : String, val date : String, val url : String):Parcelable {
}