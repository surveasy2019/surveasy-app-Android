package com.surveasy.surveasy.data.model.response

import com.surveasy.surveasy.data.mapper.DomainMapper
import com.surveasy.surveasy.data.model.BaseDataModel
import com.surveasy.surveasy.domain.model.AccountInfo

data class AccountInfoResponse(
    val rewardCurrent: Int,
    val accountOwner: String,
    val accountType: String,
    val accountNumber: String,
) : BaseDataModel {
    companion object : DomainMapper<AccountInfoResponse, AccountInfo> {
        override fun AccountInfoResponse.toDomainModel(): AccountInfo = AccountInfo(
            rewardCurrent = rewardCurrent,
            accountOwner = accountOwner,
            accountType = accountType,
            accountNumber = accountNumber
        )
    }
}
