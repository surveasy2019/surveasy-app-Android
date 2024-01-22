package com.surveasy.surveasy.domain.model

import com.surveasy.surveasy.domain.base.BaseDomainModel

data class AuthProvider(
    val authProvider: String,
) : BaseDomainModel
