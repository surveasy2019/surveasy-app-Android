package com.android.surveasy.home.banner

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import kotlinx.android.parcel.Parcelize

@Parcelize
class BannerViewModel : ViewModel(), Parcelable {
    var uriList = arrayListOf<String>()
    var num = 0
}