package com.surveasy.surveasy.data.model.request

data class KakaoInfoRequest(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val gender: String,
    val birth: String,
    val authProvider: String
)
