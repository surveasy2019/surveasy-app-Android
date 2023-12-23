package com.surveasy.surveasy.domain.model

import com.surveasy.surveasy.domain.base.BaseDomainModel

data class PanelDetailInfo(
    val name: String,
    val birth: String,
    val gender: String,
    val email: String,
    val phoneNumber: String,
    val accountOwner: String,
    val accountType: String,
    val accountNumber: String,
    val english: Boolean
) : BaseDomainModel
