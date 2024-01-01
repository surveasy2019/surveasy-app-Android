package com.surveasy.surveasy.data.model.request

data class EditInfoRequest(
    val phoneNumber: String,
    val accountOwner: String,
    val accountType: String,
    val accountNumber: String,
    val english: Boolean,
)