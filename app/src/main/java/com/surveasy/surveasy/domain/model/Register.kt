package com.surveasy.surveasy.domain.model

import com.surveasy.surveasy.domain.base.BaseDomainModel

data class Register(
    val accessToken: String,
    val refreshToken: String,
) : BaseDomainModel
