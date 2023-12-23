package com.surveasy.surveasy.data.model.request

data class NewRegisterRequest(
    val name: String,
    val email: String,
    val fcmToken: String,
    val gender: String,
    val birth: String,
    val accountOwner: String,
    val accountType: String,
    val accountNumber: String,
    val inflowPath: String,
    val inflowPathEtc: String,
    val phoneNumber: String,
    val platform: String = "ANDROID",
    val pushOn: Boolean,
    val marketing: Boolean,
)