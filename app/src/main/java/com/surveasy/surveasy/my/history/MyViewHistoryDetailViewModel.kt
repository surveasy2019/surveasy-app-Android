package com.surveasy.surveasy.my.history

import androidx.lifecycle.ViewModel

class MyViewHistoryDetailViewModel : ViewModel() {
    var progress = ArrayList<MyViewDetailProgress>()
    val detailModel = ArrayList<MyViewDetailItem>()
}