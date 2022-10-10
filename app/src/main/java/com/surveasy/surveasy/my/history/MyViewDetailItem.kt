package com.surveasy.surveasy.my.history

data class MyViewDetailItem(
    var id: Int,
    var lastId: Int,
    var type: String?,
    var date: String,
    var title: String,
    var reward: Int,

    )

data class MyViewFilePath(
    var filePath: String?
)
