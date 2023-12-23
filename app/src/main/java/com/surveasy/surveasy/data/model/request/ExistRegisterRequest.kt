package com.surveasy.surveasy.data.model.request

data class ExistRegisterRequest (
    val uid : String,
    val email: String,
    val platform: String = "ANDROID"
)