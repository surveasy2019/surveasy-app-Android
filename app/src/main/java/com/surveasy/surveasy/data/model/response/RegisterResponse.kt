package com.surveasy.surveasy.data.model.response

import com.surveasy.surveasy.data.mapper.DomainMapper
import com.surveasy.surveasy.data.model.BaseDataModel
import com.surveasy.surveasy.domain.model.Register

data class RegisterResponse(
    val accessToken: String,
    val refreshToken: String,
) : BaseDataModel {
    companion object : DomainMapper<RegisterResponse, Register> {
        override fun RegisterResponse.toDomainModel(): Register = Register(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}
