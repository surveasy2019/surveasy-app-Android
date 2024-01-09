package com.surveasy.surveasy.data.model.response

import com.surveasy.surveasy.data.mapper.DomainMapper
import com.surveasy.surveasy.data.model.BaseDataModel
import com.surveasy.surveasy.domain.model.PanelDetailInfo

data class PanelDetailInfoResponse(
    val name: String,
    val birth: String,
    val gender: String,
    val email: String,
    val phoneNumber: String,
    val accountOwner: String,
    val accountType: String,
    val accountNumber: String,
    val english: Boolean,
) : BaseDataModel {
    companion object : DomainMapper<PanelDetailInfoResponse, PanelDetailInfo> {
        override fun PanelDetailInfoResponse.toDomainModel(): PanelDetailInfo = PanelDetailInfo(
            name = name,
            birth = birth,
            gender = gender,
            email = email,
            phoneNumber = phoneNumber,
            accountOwner = accountOwner,
            accountType = accountType,
            accountNumber = accountNumber,
            english = english
        )

    }
}
