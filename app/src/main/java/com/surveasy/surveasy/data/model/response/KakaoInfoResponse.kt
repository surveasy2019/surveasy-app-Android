package com.surveasy.surveasy.data.model.response

import com.surveasy.surveasy.data.mapper.DomainMapper
import com.surveasy.surveasy.data.model.BaseDataModel
import com.surveasy.surveasy.data.model.response.TokenResponse.Companion.toDomainModel
import com.surveasy.surveasy.domain.model.KakaoInfo

data class KakaoInfoResponse(
    val additionalInfo: Boolean,
    val tokens: TokenResponse,
    val signedUp: Boolean,
) : BaseDataModel {
    companion object : DomainMapper<KakaoInfoResponse, KakaoInfo> {
        override fun KakaoInfoResponse.toDomainModel(): KakaoInfo = KakaoInfo(
            additionalInfo = additionalInfo,
            tokens = tokens.toDomainModel(),
            signedUp = signedUp
        )
    }
}
