package com.surveasy.surveasy.data.model.request

data class FsRequest(
    val english: Boolean,
    val city: String,
    val district: String,
    val family: String,
    val houseType: String,
    val job: String,
    val university: String,
    val major: String,
    val marriage: String,
    val military: String,
    val pet: String,
)
