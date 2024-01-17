package com.surveasy.surveasy.domain.model

import com.surveasy.surveasy.domain.base.BaseDomainModel

data class Token(
    val accessToken: String,
    val refreshToken: String
) : BaseDomainModel
