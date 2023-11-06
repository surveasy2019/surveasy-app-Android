package com.surveasy.surveasy.data.model.response

data class SignupResponse(
    val panelId: Int,
    val accessToken: String,
    val refreshToken: String
)