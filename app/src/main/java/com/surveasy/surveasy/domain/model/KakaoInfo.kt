package com.surveasy.surveasy.domain.model

import com.surveasy.surveasy.domain.base.BaseDomainModel

data class KakaoInfo(
    val additionalInfo: Boolean,
    val tokens: Token,
    val signedUp: Boolean,
) : BaseDomainModel
