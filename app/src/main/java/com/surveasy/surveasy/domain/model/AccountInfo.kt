package com.surveasy.surveasy.domain.model

import com.surveasy.surveasy.domain.base.BaseDomainModel

data class AccountInfo(
    val rewardCurrent: Int,
    val accountOwner: String,
    val accountType: String,
    val accountNumber: String,
) : BaseDomainModel
