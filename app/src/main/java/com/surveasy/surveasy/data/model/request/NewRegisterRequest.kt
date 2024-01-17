package com.surveasy.surveasy.data.model.request

data class NewRegisterRequest(
    val platform: String,
    val accountOwner: String,
    val accountType: String,
    val accountNumber: String,
    val inflowPath: String,
    val inflowPathEtc: String?,
    val pushOn: Boolean,
    val marketingAgree: Boolean,
)