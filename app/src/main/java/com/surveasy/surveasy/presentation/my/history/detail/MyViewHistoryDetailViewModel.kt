package com.surveasy.surveasy.my.history.detail

import androidx.lifecycle.ViewModel

class MyViewHistoryDetailViewModel : ViewModel() {

    var progress = ArrayList<MyViewDetailProgress>()
    val detailModel = ArrayList<MyViewDetailItem>()
    var filePath = ArrayList<MyViewFilePath>()

    init {
        filePath.clear()
    }

}