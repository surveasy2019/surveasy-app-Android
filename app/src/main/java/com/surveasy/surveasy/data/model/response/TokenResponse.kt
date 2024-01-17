package com.surveasy.surveasy.data.model.response

import com.surveasy.surveasy.data.mapper.DomainMapper
import com.surveasy.surveasy.data.model.BaseDataModel
import com.surveasy.surveasy.domain.model.Token

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
) : BaseDataModel {
    companion object : DomainMapper<TokenResponse, Token> {
        override fun TokenResponse.toDomainModel(): Token = Token(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}
