package com.surveasy.surveasy.data.model.request

data class ExistRegisterRequest(
    val uid: String,
    val email: String,
    val password: String,
    val platform: String = "ANDROID"
)