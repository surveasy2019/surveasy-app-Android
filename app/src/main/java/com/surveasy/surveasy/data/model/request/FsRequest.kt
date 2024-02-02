package com.surveasy.surveasy.data.model.request

data class FsRequest(
    val english: Boolean,
    val city: String,
    val family: String,
    val job: String,
    val major: String?,
    val pet: String,
)
