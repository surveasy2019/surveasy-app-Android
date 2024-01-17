package com.surveasy.surveasy.domain.model

import com.surveasy.surveasy.domain.base.BaseDomainModel

data class Register(
    val panelId: Int,
    val accessToken: String?,
    val refreshToken: String?,
) : BaseDomainModel
