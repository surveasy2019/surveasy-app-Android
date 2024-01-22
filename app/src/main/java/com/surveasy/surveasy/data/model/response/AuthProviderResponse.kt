package com.surveasy.surveasy.data.model.response

import com.surveasy.surveasy.data.mapper.DomainMapper
import com.surveasy.surveasy.data.model.BaseDataModel
import com.surveasy.surveasy.domain.model.AuthProvider

data class AuthProviderResponse(
    val authProvider: String,
) : BaseDataModel {
    companion object : DomainMapper<AuthProviderResponse, AuthProvider> {
        override fun AuthProviderResponse.toDomainModel(): AuthProvider = AuthProvider(
            authProvider = authProvider
        )
    }
}
